import io
import itertools
import os

import requests
import queue
from threading import Thread
import pandas as pd

# python script to scrape last 5 years of problems in all 4 divisions

url_temp = 'http://usaco.org/index.php?page=viewproblem2&cpid={}'
index_url = 'http://usaco.org/index.php?page={}{}results'

prob_format = 'http://usaco.org/{}'

spreadsheet_url = '\"=HYPERLINK(""{}\"\",\"\"{}\"\")\"'

# print(spreadsheet_url)

months = ['dec', 'jan', 'feb', 'open']

id_interval = 24


def getProbUrl(s):
    s = s.split('\'')
    return s[1]


# 4 divsions over 4 years
problems = [[[] for i in range(5)] for i in range(4)]
cnt = [[0 for i in range(5)] for i in range(4)]

# print(problems)

chkS = set()

for year in range(20, 15, -1):
    for m in range(0, 4):
        # print("year, m", year, m);
        if year is 17 and m is 3:  # problem removed that contest
            continue

        c_index_url = index_url.format(months[m], year if m != 0 else year - 1)

        page = requests.get(c_index_url).text
        lines = page.split('\n')
        prob_urls = [getProbUrl(x) for x in lines if 'cpid' in x]
        assert len(prob_urls) is 12

        for d in range(0, 4):
            for i in range(d * 3, d * 3 + 3):
                problems[d][year - 16].append(prob_urls[i])
                cnt[d][year - 16] += 1

with open('output.txt', 'w') as f:
    for d in range(0, 4):
        for year in range(20, 15, -1):

            for prob_url in problems[d][year - 16]:
                assert (prob_url not in chkS)
                chkS.add(prob_url)

                full_prob_url = prob_format.format(prob_url)

                page = requests.get(full_prob_url).text

                prob_name = page.split('\n')[64].split('.')[1].replace('</h2>', '').strip()
                print(prob_name)

                cell_str = spreadsheet_url.format(full_prob_url, prob_name)

                f.write(cell_str)
                f.write(',')

            f.write('\n')
        f.write('\n')
