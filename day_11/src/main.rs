use std::collections::HashMap;
use std::env;
use std::fs;

mod game;
use game::grid::*;
use game::square;

fn load_grid() -> Grid {
    let content = fs::read_to_string("input.txt").expect("Could not read file");

    let lines = content.split("\n");

    let mut grid = Grid {
        data: HashMap::new(),
    };

    let mut y = 0;
    for line in lines {
        let mut x = 0;
        for c in line.chars() {
            let square = square::parse(c);
            let value = square.unwrap();
            grid.set_square(x, y, value);
            x += 1;
        }
        y += 1;
    }

    return grid;
}

fn main() {
    part1(&mut load_grid());
    part2(&mut load_grid());
}

fn part1(grid: &mut Grid) {
    let data = grid.data.clone();

    loop {
        let mut changes = Vec::<(i8, i8, square::Square)>::new();
        let mut changed = false; //If the grid has changed at all
        for loc in data.keys() {
            let square = grid.get_square(loc.0, loc.1).unwrap(); 
            let new_square = square::process(square, *loc, &grid); //Process the square
            if new_square.is_some() {
                changed = true;
                changes.push((loc.0, loc.1, new_square.unwrap())); //Add to the changed vector if the square has changed
            }
        }

        for (x, y, sq) in changes {
            grid.set_square(x, y, sq); //Then update the grid with changes
        }

        if !changed {
            break; //If the grid stays the same, stop looping
        }
    }
    println!(
        "{:?}",
        grid.data
            .values()
            .filter(|o| **o == square::Square::Occupied)
            .count()
    );

}

fn part2(grid: &mut Grid) {
    let data = grid.data.clone();

    loop {
        let mut changes = Vec::<(i8, i8, square::Square)>::new();
        let mut changed = false; //If the grid has changed at all
        for loc in data.keys() {
            let square = grid.get_square(loc.0, loc.1).unwrap(); 
            let new_square = square::process_2(square, *loc, &grid); //Process the square
            if new_square.is_some() {
                changed = true;
                changes.push((loc.0, loc.1, new_square.unwrap())); //Add to the changed vector if the square has changed
            }
        }

        for (x, y, sq) in changes {
            grid.set_square(x, y, sq); //Then update the grid with changes
        }

        if !changed {
            break; //If the grid stays the same, stop looping
        }
    }
    println!(
        "{:?}",
        grid.data
            .values()
            .filter(|o| **o == square::Square::Occupied)
            .count()
    );
}