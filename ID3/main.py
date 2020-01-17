import csv
import copy

from math import log2
from random import shuffle

DECISION_ATTRIBUTE = "class"
DECISION_POSITIVE = "recurrence-events"
DECISION_NEGATIVE = "no-recurrence-events"


def get_dataset():
    with open("breast-cancer.data") as csvfile:
        csv_reader = csv.DictReader(csvfile)

        lines = [dict(line) for line in list(csv_reader)]
        return lines


def get_entropy(countPositive, countNegative):
    sum = countNegative + countPositive

    positivePercent = countPositive / sum
    negativePercent = countNegative / sum

    positiveLog = log2(positivePercent) if positivePercent != 0 else 0
    negativeLog = log2(negativePercent) if negativePercent != 0 else 0

    return -positivePercent * positiveLog - negativePercent * negativeLog


def get_entropy_of_attribute(attribute, options, dataset):

    entropies = dict.fromkeys(options)
    count_attribute = len([p for p in dataset if p[attribute] != '?'])

    for option in options:
        count_option = len([p for p in dataset if p[attribute] == option])
        count_positive = [p for p in dataset if p[attribute] == option and p[DECISION_ATTRIBUTE] == DECISION_POSITIVE]
        count_negative = [p for p in dataset if p[attribute] == option and p[DECISION_ATTRIBUTE] == DECISION_NEGATIVE]

        entropies[option] = count_option / count_attribute * get_entropy(len(count_positive), len(count_negative))

    return sum([e for (key, e) in entropies.items()])


def is_dataset_with_same_outcome(dataset):
    classes = set([entry[DECISION_ATTRIBUTE] for entry in dataset])
    return len(classes) == 1


def id3(dataset):
    if is_dataset_with_same_outcome(dataset):
        return dataset[0][DECISION_ATTRIBUTE]

    # dict containing the possible values for each attribute
    attribute_options = {}

    # split the dataset in attributes
    for attribute in dataset[0]:
        if attribute == "class":
            continue

        possible_attributes = set([entry[attribute] for entry in dataset])
        possible_attributes.discard('?')

        attribute_options[attribute] = list(possible_attributes)

    entropies = {attribute: get_entropy_of_attribute(attribute, options, dataset)
                 for (attribute, options) in attribute_options.items()}

    min_entropy = 1
    best_attribute = None
    for attribute, entropy in entropies.items():
        if entropy <= min_entropy:
            best_attribute = attribute

    # We have no more attributes, mark this as a node with the most frequent
    if best_attribute == None:
        count_positive = len([p for p in dataset if p[DECISION_ATTRIBUTE] == DECISION_POSITIVE])
        count_negative = len([p for p in dataset if p[DECISION_ATTRIBUTE] == DECISION_NEGATIVE])

        if count_positive >= count_negative:
            return DECISION_POSITIVE

        return DECISION_NEGATIVE

    children = {}
    for option in attribute_options[best_attribute]:
        dataset_with_option = copy.deepcopy([d for d in dataset if d[best_attribute] == option])

        # delete the attribute from the new dataset
        for i in dataset_with_option:
            del i[attribute]

        children[option] = id3(dataset_with_option)

    return {best_attribute: children}


def prune_tree(tree, validationset):
    


def get_decision(data_entry, tree):
    # we have just one enty in the tree, the root
    for (key, children) in tree.items():
        data_entry_value = data_entry[key]

        # decide on a path
        path = children[data_entry_value]

        if not isinstance(path, dict):
            return path
        return get_decision(data_entry, path)


def run_set_through_tree(testset, tree):
    correct_count = 0
    unknowns = 0
    for entry in testset:
        try:
            decision = get_decision(entry, tree)

            if decision == entry["class"]:
                correct_count += 1
        except KeyError as k:
            # print("Unknown value: {}".format(k))
            unknowns += 1

    return correct_count / (len(testset) - unknowns)


def run_alg(dataset, validationset, testset):
    tree = id3(dataset)

    prune_tree(tree, validationset)

    return run_set_through_tree(testset, tree)


def main():
    data = get_dataset()
    shuffle(data)

    ratio = 10
    testset_size = int(len(data) / ratio)

    sum = 0
    for i in range(0, len(data) - (len(data) % testset_size), testset_size):
        testset = data[i:i + testset_size]
        dataset = data[0:i] + data[i + testset_size:]

        accuracy = run_alg(dataset, testset)
        print(accuracy)
        sum += accuracy

    print("Average accuracy: {}".format(sum / ratio))


if __name__ == "__main__":
    main()