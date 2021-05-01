import re
import threading
import time

import requests
from bs4 import BeautifulSoup
import numpy as np

URL_PATTERN = r'(https?:\/\/)([\w]+.[\w]+)(?:\/[\w\.]*)*\/?'


def clean_links(links):
    urls = []
    for link in links:
        if link.has_attr('href'):
            link_match = re.search(URL_PATTERN, link['href'])
            if link_match is not None:
                urls.append(link_match.group(0))
    return urls


def parse_urls(page):
    soup = BeautifulSoup(page.text, "html.parser")
    urls = clean_links(soup.find_all('a'))
    return urls


def parse_urls_dic(url_of_page, depth=1, max_depth=1):
    page = requests.get(url_of_page)
    urls = {}
    links = parse_urls(page)[15:21]
    if depth < max_depth:
        for url in links:
            urls[url] = parse_urls_dic(url, depth=depth+1, max_depth=max_depth)
        return urls
    else:
        return links


def start_parse(url) -> dict:
    return parse_urls_dic(url, max_depth=3)


def parsing_execution_time(url) -> dict:
    start_time = time.time()
    urls_topology = start_parse(url)
    print("time: {}".format(time.time() - start_time))
    return urls_topology


def set_of_unique_elements(dictionary: dict, unique_set=None, start=False):
    if start:
        unique_set = set()
        set_of_unique_elements(dictionary, unique_set=unique_set)
        return unique_set
    for key in dictionary.keys():
        if dictionary[key].__class__ == dict:
            unique_set.add(key)
            set_of_unique_elements(dictionary[key], unique_set=unique_set)
        elif dictionary[key].__class__ == list:
            for elem in dictionary[key]:
                unique_set.add(elem)


def get_matrix(unique_elements: set):
    matrix = {}
    for columns in unique_elements:
        matrix[columns] = {}
        for rows in unique_elements:
            matrix[columns][rows] = 0
    return matrix


def fill_matrix(matrix: dict, topology_of_elements: dict):
    for columns in topology_of_elements.keys():
        count_of_elements = len(topology_of_elements[columns])
        count = 0 if count_of_elements == 0 else 1 / count_of_elements
        for rows in topology_of_elements[columns]:
            matrix[columns][rows] = count


def matrix_of_transition(topology: dict):
    transition_matrix = get_matrix(set_of_unique_elements(topology, start=True))
    fill_matrix(transition_matrix, topology)
    return transition_matrix


def mark_elements(transition_matrix: dict):
    compair_of_elements = []
    for elements in transition_matrix:
        compair_of_elements.append(elements)

    return compair_of_elements


def change_structure_of_matrix(transition_matrix: dict):
    matrix_with_enumerated_elements = []

    elements_of_matrix = []
    for elems in transition_matrix.keys():
        elements_of_matrix.append(elems)

    i = 0
    for rows in elements_of_matrix:
        matrix_with_enumerated_elements.append([])
        for columns in elements_of_matrix:
            matrix_with_enumerated_elements[i].append(transition_matrix[columns][rows])
        i += 1

    return np.array(matrix_with_enumerated_elements)


def get_page_rank(coef, matrix, vector):
    first_term = matrix.dot(vector) * coef
    unit_vector = []
    for i in range(len(first_term)):
        unit_vector.append(1)
    second_term = np.array(unit_vector) * ((1 - coef) / len(first_term))
    return first_term + second_term


def get_vector_of_iterations(transition_matrix, coef, count_of_iterations: int):
    vector = []
    dimension_of_matrix = len(transition_matrix)
    for i in range(dimension_of_matrix):
        vector.append(1 / dimension_of_matrix)

    result_vectors = [np.array(vector)]

    for i in range(count_of_iterations):
        result_vectors.append(get_page_rank(coef, transition_matrix, result_vectors[i - 1]))

    return result_vectors


def compair_link_pagerank(compair_link: list, pr_vector: list):
    answer_dic = {}
    for i in range(len(pr_vector)):
        answer_dic[compair_link[i]] = pr_vector[i]

    return answer_dic


def show_vector_for_iterations(transition_matrix: dict, coef, count_of_iterations: int):
    iteration_vector = get_vector_of_iterations(change_structure_of_matrix(transition_matrix), coef, count_of_iterations)
    for i in range(len(iteration_vector[-1])):
        print('{} Pagerank: {}'.format(i, iteration_vector[-1][i]))


def show_links_with_biggest_pr(link_pagerank: dict):
    answer_dic = {k: v for k, v in sorted(link_pagerank.items(), key=lambda item: item[1])}
    i = 1
    for key in answer_dic.keys():
        if i > len(answer_dic):
            break
        print('{}: {}'.format(i, key))
        i += 1


if __name__ == '__main__':
    URL = input("Введите ссылку: ")
    print('01: ', end='')
    links = parsing_execution_time(URL)
    print('02: ', end='')
    links_matrix = matrix_of_transition(links)
    print(links_matrix)
    print('03: links page rank')
    show_vector_for_iterations(links_matrix, 0.8, 35)
    print('04: ')
    pagerank_vector = get_vector_of_iterations(change_structure_of_matrix(links_matrix), 0.8, 35)
    show_links_with_biggest_pr(compair_link_pagerank(mark_elements(links_matrix), pagerank_vector[-1]))
