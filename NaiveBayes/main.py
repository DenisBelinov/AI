import csv
from random import shuffle


def get_dataset():
    with open("house-votes-84.data") as csvfile:
        #csv_reader = csv.reader(csvfile, delimiter=',')
        csv_reader = csv.DictReader(csvfile)

        lines = [dict(line) for line in list(csv_reader)]
        return lines


def run_alg(dataset, testset):
    ### SETUP
    republicans = [line for line in dataset if line["party"] == "republican"]
    democrats = [line for line in dataset if line["party"] == "democrat"]

    republican_probability = len(republicans) / len(dataset)
    democrat_probability = len(democrats) / len(dataset)

    features_count = len(dataset[1].keys())
    probabilities = {}
    for feature in dataset[1].keys():
        if feature == "party":
            continue

        count_relevant_republicans = len([line for line in republicans if line[feature] != "?"])
        count_relevant_democrats = len([line for line in democrats if line[feature] != "?"])

        count_yes_republicans = len([line for line in republicans if line[feature] == "y"])
        count_no_republicans = len([line for line in republicans if line[feature] == "n"])

        count_yes_democrats = len([line for line in democrats if line[feature] == "y"])
        count_no_democrats = len([line for line in democrats if line[feature] == "n"])

        yes_given_republican_probability = count_yes_republicans / count_relevant_republicans
        no_given_republican_probability = count_no_republicans / count_relevant_republicans

        yes_given_democrat_probability = count_yes_democrats / count_relevant_democrats
        no_given_democrat_probability = count_no_democrats / count_relevant_democrats

        probabilities[feature] = {"yes_given_republican": yes_given_republican_probability,
                                  "yes_given_democrat": yes_given_democrat_probability,
                                  "no_given_republican": no_given_republican_probability,
                                  "no_given_democrat": no_given_democrat_probability}

    ### Guess
    correct_count = 0
    for line in testset:
        is_republican_probability = republican_probability
        is_democrat_probability = democrat_probability

        for k, v in line.items():
            if k == "party" or v == "?":
                continue

            if v == "y":
                is_republican_probability *= probabilities[k]["yes_given_republican"]
                is_democrat_probability *= probabilities[k]["yes_given_democrat"]
            else:
                is_republican_probability *= probabilities[k]["no_given_republican"]
                is_democrat_probability *= probabilities[k]["no_given_democrat"]

        prediction = "republican"
        if is_democrat_probability > is_republican_probability:
            prediction = "democrat"

        if line["party"] == prediction:
            correct_count += 1

    accuracy = correct_count/len(testset)
    print("accuracy: {}".format(accuracy))

    return accuracy


def main():
    data = get_dataset()

    ratio = 10
    testset_size = int(len(data) / ratio)

    sum = 0
    for i in range(0, len(data) - (len(data) % testset_size), testset_size):
        testset = data[i:i + testset_size]
        dataset = data[0:i] + data[i+testset_size:]

        sum += run_alg(dataset, testset)

    print("Average accuracy: {}".format(sum/ratio))
if __name__ == "__main__":
    main()