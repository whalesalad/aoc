use clap::{Parser, Subcommand};

mod days;

#[derive(Parser)]
#[command(author, version, about, long_about = None)]
struct Cli {
    #[command(subcommand)]
    command: Commands,
}

#[derive(Subcommand)]
enum Commands {
    /// Run solutions for a specific day
    Run {
        /// The day to run (e.g., 1 for Day 1)
        day: u8,

        /// Specify which part to run: "example", "part1", "part2", or "all"
        #[arg(default_value = "all")]
        part: String,
    },
}

fn main() {
    let cli = Cli::parse();

    match cli.command {
        Commands::Run { day, part } => {
            let input_example = format!("data/day{:02}-example.txt", day);
            let input_part1 = format!("data/day{:02}-part1.txt", day);
            let input_part2 = format!("data/day{:02}-part2.txt", day);

            match part.as_str() {
                "example" => run_example(day, &input_example),
                "part1" => run_part(day, &input_part1, 1),
                "part2" => run_part(day, &input_part2, 2),
                "all" => {
                    run_example(day, &input_example);
                    run_part(day, &input_part1, 1);
                    run_part(day, &input_part2, 2);
                }
                _ => eprintln!("Unknown part: {}", part),
            }
        }
    }
}

fn run_example(day: u8, input_file: &str) {
    let input = std::fs::read_to_string(input_file).expect("Failed to read example input");
    let output = match day {
        1 => days::day01::part1(&input), // Adjust for other parts if needed
        _ => "Not implemented".to_string(),
    };
    println!("Day {} Example: {}", day, output);
}

fn run_part(day: u8, input_file: &str, part: u8) {
    let input = std::fs::read_to_string(input_file).expect("Failed to read input");
    let output = match (day, part) {
        (1, 1) => days::day01::part1(&input),
        (1, 2) => days::day01::part2(&input),
        _ => "Not implemented".to_string(),
    };
    println!("Day {} Part {}: {}", day, part, output);
}
