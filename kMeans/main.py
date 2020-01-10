import sys
import time
import statistics
import matplotlib.pyplot as plt

from random import seed
from random import random
from math import sqrt

COLORS = ['b', 'g', 'r', 'c', 'm', 'y', 'k',"xkcd:sky blue"]
seed(time)


def get_random_number_in_range(min, max):
    return min + (random() * (max - min))


def calculate_distance(p1, p2):
    return sqrt((p2[0] - p1[0]) * (p2[0] - p1[0]) + (p2[1] - p1[1]) * (p2[1] - p1[1]))


def get_average_point(points):
    xs = [point[0] for point in points]
    ys = [point[1] for point in points]

    return statistics.mean(xs), statistics.mean(ys)


def are_clusters_the_same(c1, c2):
    sorted_c1 = sorted(c1.keys())
    sorted_c2 = sorted(c2.keys())

    # check if the centroids are the same
    if sorted_c1 != sorted_c2:
        return False

    # check if the points are the same for each centroid
    for centroid in sorted_c1:
        if c1[centroid] != c2[centroid]:
            return False

    return True


def populate_clusters(points, clusters):
    for point in points:
        nearest_centroid = None
        min_centroi_dist = 10000000000

        for centroid in clusters:
            dist = calculate_distance(point, centroid)
            if dist < min_centroi_dist:
                nearest_centroid = centroid
                min_centroi_dist = dist

        clusters[nearest_centroid].append(point)

def main(file, k):
    k = int(k)

    with open(file, "r") as f:
        lines = f.readlines()
        points = [(float(line.split()[0]), float(line.split()[1])) for line in lines]

    xs = [point[0] for point in points]
    ys = [point[1] for point in points]

    max_x = max(xs)
    min_x = min(xs)
    max_y = max(ys)
    min_y = min(ys)

    clusters = {}
    # get first centroids
    # TODO: try getting random points from the given ones for the initial centroid
    for i in range(0, k):
        random_centroid = (get_random_number_in_range(min_x, max_x),
                          get_random_number_in_range(min_y, max_y))
        clusters[random_centroid] = []

    # get first clusters
    populate_clusters(points, clusters)

    # run the algorithm
    changed = False
    while(changed):
        new_clusters = {}
        for centroid, points in clusters.items():
            new_clusters[get_average_point(points)] = []

        populate_clusters(points, new_clusters)

        changed = not are_clusters_the_same(clusters, new_clusters)
        clusters = new_clusters

    color_index = 0
    for centroid, points in clusters.items():
        xs = [point[0] for point in points]
        ys = [point[1] for point in points]

        plt.plot(xs, ys, "o", color=COLORS[color_index])

        color_index += 1

    plt.show()


if __name__ == "__main__":
    main(sys.argv[1], sys.argv[2])