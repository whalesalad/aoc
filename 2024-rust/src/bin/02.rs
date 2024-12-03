advent_of_code::solution!(2);

fn parse_line(line: &str) -> Vec<u8> {
    line.split_whitespace()
        .filter_map(|num| num.parse::<u8>().ok())
        .collect()
}

fn parse_input(input: &str) -> Vec<Vec<u8>> {
    input.lines().map(parse_line).collect()
}

fn increasing_or_decreasing(levels: &Vec<u8>) -> bool {
    let increasing = levels[1] > levels[0];

    for pair in levels.windows(2) {
        if pair[1] > pair[0] && !increasing {
            return false;
        }
        if pair[1] < pair[0] && increasing {
            return false;
        }
    }

    true
}

fn adjacent_level_check(levels: &Vec<u8>) -> bool {
    // any two adjacent levels differ by at least one and at most three.
    // so we'll run a predicate over each pair of adjacent levels.
    // and sum of pair needs to be > 1 and < 4.
    levels.windows(2).all(|pair| {
        let diff = pair[1].abs_diff(pair[0]);
        diff >= 1 && diff <= 3
    })
}

fn safe_levels(levels: &Vec<u8>) -> bool {
    increasing_or_decreasing(levels) && adjacent_level_check(levels)
}

fn safe_levels_allow_one_bad_level(levels: &Vec<u8>) -> bool {
    let original = safe_levels(levels);

    if original {
        return true;
    }

    // else... loop through permutations of the levels where we exclude one level
    // ie, if original is [1, 2, 3, 4, 5]
    // we'll test [1, 2, 3, 4], [1, 2, 3, 5], [1, 2, 4, 5], [1, 3, 4, 5], [2, 3, 4, 5]
    // and if any of those are safe_levels, return true.
    for i in 0..levels.len() {
        let mut clone = levels.clone();
        clone.remove(i);
        if safe_levels(&clone) {
            return true;
        }
    }

    false
}

pub fn part_one(input: &str) -> Option<usize> {
    /*
    7 6 4 2 1

    becomes

    [7 6] [6 4] [4 2] [2 1]

    each pair needs to pass:
        -

    */
    // println!("input: {:#?}", input);

    let parsed = parse_input(input);

    let out = parsed
        .into_iter()
        .filter(|levels| safe_levels(levels))
        .count();

    Some(out)
}

pub fn part_two(input: &str) -> Option<usize> {
    let parsed = parse_input(input);

    let out = parsed
        .into_iter()
        .filter(|levels| safe_levels_allow_one_bad_level(levels))
        .count();

    Some(out)
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn test_increasing_or_decreasing() {
        assert_eq!(increasing_or_decreasing(&vec![1, 2, 3, 4, 5]), true);
        assert_eq!(increasing_or_decreasing(&vec![5, 4, 3, 2, 1]), true);
        assert_eq!(increasing_or_decreasing(&vec![1, 3, 2, 4, 5]), false);
        assert_eq!(increasing_or_decreasing(&vec![7, 6, 4, 2, 1]), true);
    }

    #[test]
    fn test_adjacent_level_check() {
        assert_eq!(adjacent_level_check(&vec![7, 6, 4, 2, 1]), true);
        assert_eq!(adjacent_level_check(&vec![1, 2, 7, 8, 9]), false);
    }

    #[test]
    fn test_part_one() {
        let result = part_one(&advent_of_code::template::read_file("examples", DAY));
        assert_eq!(result, Some(2));
    }

    #[test]
    fn test_safe_levels_allow_one_bad_level() {
        assert_eq!(safe_levels_allow_one_bad_level(&vec![1, 3, 2, 4, 5]), true);
    }

    #[test]
    fn test_part_two() {
        let result = part_two(&advent_of_code::template::read_file("examples", DAY));
        assert_eq!(result, None);
    }
}
