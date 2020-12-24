use super::square::*;
use std::collections::HashMap;

pub struct Grid {
    pub data: HashMap<(i8, i8), Square>,
}

impl Grid {
    pub fn get_square(&self, x: i8, y: i8) -> Option<Square> {
        self.data.get(&(x, y)).map(|x| *x)
    }

    pub fn set_square(&mut self, x: i8, y: i8, square: Square) {
        self.data.insert((x, y), square);
    }
}
