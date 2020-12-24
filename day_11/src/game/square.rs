use super::grid::Grid;

#[derive(Copy, Clone, Debug, PartialEq)]
pub enum Square {
    Floor,
    Empty,
    Occupied,
}

pub fn process(square: Square, pos: (i8, i8), grid: &Grid) -> Option<Square> {
    match square {
        Square::Floor => None,
        Square::Empty => {
            let adjacent = [
                grid.get_square(pos.0, pos.1 + 1),
                grid.get_square(pos.0 + 1, pos.1),
                grid.get_square(pos.0 - 1, pos.1),
                grid.get_square(pos.0, pos.1 - 1),
                grid.get_square(pos.0 + 1, pos.1 + 1),
                grid.get_square(pos.0 + 1, pos.1 - 1),
                grid.get_square(pos.0 - 1, pos.1 + 1),
                grid.get_square(pos.0 - 1, pos.1 - 1),
            ];
            let mut adj_squares = adjacent.iter().map(|opt| opt.unwrap_or(Square::Floor));

            // If none of the adjacents are occupied, then we make this one occupied
            if adj_squares.all(|sq| sq != Square::Occupied) {
                return Some(Square::Occupied);
            }

            return None;
        }
        Square::Occupied => {
            let adjacent = [
                grid.get_square(pos.0, pos.1 + 1),
                grid.get_square(pos.0 + 1, pos.1),
                grid.get_square(pos.0 - 1, pos.1),
                grid.get_square(pos.0, pos.1 - 1),
                grid.get_square(pos.0 + 1, pos.1 + 1),
                grid.get_square(pos.0 + 1, pos.1 - 1),
                grid.get_square(pos.0 - 1, pos.1 + 1),
                grid.get_square(pos.0 - 1, pos.1 - 1),
            ];
            let adj_squares = adjacent.iter().map(|opt| opt.unwrap_or(Square::Floor));

            if adj_squares.filter(|sq| *sq == Square::Occupied).count() >= 4 {
                return Some(Square::Empty);
            }
            return None;
        }
    }
}

fn get_first_visible_adjacent(pos: (i8, i8), grid: &Grid) -> Vec<Square> {
    let mut adjacent = Vec::<Square>::new();

    for x in -1..2 {
        //-1 to 1
        for y in -1..2 {
            //-1 to 1
            if x == 0 && y == 0 {
                continue;
            }
            let mut dir_x: i8 = x;
            let mut dir_y: i8 = y;
            let mut square = Square::Floor;

            loop {
                if dir_x.checked_add(pos.0).is_none() {
                    break;
                }
                if dir_y.checked_add(pos.1).is_none() {
                    break;
                }
                let pos_x = dir_x + pos.0;
                let pos_y = dir_y + pos.1;
                let sq = grid.get_square(pos_x, pos_y);

                if sq.is_none() {
                    //Reached the edge of the board
                    break;
                }
                if sq.unwrap() != Square::Floor {
                    square = sq.unwrap();
                    break;
                }

                dir_x += x;
                dir_y += y;
            }
            adjacent.push(square);
        }
    }
    return adjacent;
}

pub fn process_2(square: Square, pos: (i8, i8), grid: &Grid) -> Option<Square> {
    match square {
        Square::Floor => None,
        Square::Empty => {
            let adjacent = get_first_visible_adjacent(pos, grid);
            let mut adj_squares = adjacent.iter().map(|sq| *sq);
            // If none of the adjacents are occupied, then we make this one occupied
            if adj_squares.all(|sq| sq != Square::Occupied) {
                return Some(Square::Occupied);
            }

            return None;
        }
        Square::Occupied => {
            let adjacent = get_first_visible_adjacent(pos, grid);
            let adj_squares = adjacent.iter().map(|sq| *sq);
            if adj_squares.filter(|sq| *sq == Square::Occupied).count() >= 5 {
                return Some(Square::Empty);
            }
            return None;
        }
    }
}

pub fn parse(c: char) -> Option<Square> {
    match c {
        '.' => Some(Square::Floor),
        'L' => Some(Square::Empty),
        '#' => Some(Square::Occupied),
        _ => None,
    }
}

pub fn to_char(square: Square) -> char {
    match square {
        Square::Floor => '.',
        Square::Empty => 'L',
        Square::Occupied => '#',
    }
}
