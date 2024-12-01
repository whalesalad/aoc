advent_of_code::solution!(1);

fn parse_input(input: &str) -> (Vec<i32>, Vec<i32>) {
    let mut left = Vec::new();
    let mut right = Vec::new();

    for line in input.lines() {
        let nums: Vec<i32> = line
            .split_whitespace()
            .filter_map(|num| num.parse::<i32>().ok())
            .collect();

        if nums.len() == 2 {
            left.push(nums[0]);
            right.push(nums[1]);
        }
    }

    (left, right)
}

fn calculate_distances(left: Vec<i32>, right: Vec<i32>) -> Vec<i32> {
    // Sort the input vectors and create iterators
    let mut left_sorted = left.into_iter().collect::<Vec<_>>();
    let mut right_sorted = right.into_iter().collect::<Vec<_>>();

    left_sorted.sort_unstable();
    right_sorted.sort_unstable();

    // Use an iterator to zip the two sorted vectors and calculate distances
    left_sorted
        .into_iter()
        .zip(right_sorted.into_iter())
        .map(|(l, r)| (l - r).abs())
        .collect()
}

fn similarity_scores(left: Vec<i32>, right: Vec<i32>) -> Vec<i32> {
    left.into_iter()
        .map(|val| {
            let occurrences = right.iter().filter(|&&r| r == val).count() as i32;
            val * occurrences
        })
        .collect()
}

pub fn part_one(input: &str) -> Option<u32> {
    let (left, right) = parse_input(input);
    let distances: Vec<i32> = calculate_distances(left, right);
    let sum: i32 = distances.iter().sum();

    Some(sum.try_into().unwrap())
}

pub fn part_two(input: &str) -> Option<u32> {
    let (left, right) = parse_input(input);
    let scores: Vec<i32> = similarity_scores(left, right);
    // println!("scores: {:#?}", scores);
    let sum: i32 = scores.iter().sum();

    Some(sum.try_into().unwrap())
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn test_part_one() {
        let result = part_one(&advent_of_code::template::read_file("examples", DAY));
        assert_eq!(result, Some(11));
    }

    #[test]
    fn test_part_two() {
        let result = part_two(&advent_of_code::template::read_file("examples", DAY));
        assert_eq!(result, Some(31));
    }
}
