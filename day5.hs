import qualified Data.Text as T
import Data.Text.IO

sliceRange :: Int -> Int -> [a] -> [a]
sliceRange from to xs = take (to - from + 1) (drop from xs)

slice :: Int -> Int -> T.Text -> T.Text
slice from to xs = T.take (to - from + 1) (T.drop from xs)

parseRow :: [Int] -> Char -> [Int]
parseRow range symbol = if symbol == 'F'
    then (sliceRange 0 ((length range) `div` 2) range) 
    else (sliceRange ((length range) `div` 2) (length range) range)

parseRowLine :: [Int] -> T.Text -> Int
parseRowLine [elem] line = elem
parseRowLine range text = case T.unpack text of
     [x] -> parseRow range x !! 0
     _ -> parseRowLine (parseRow range (T.head text)) (T.drop 1 text)


parseCol :: [Int] -> Char -> [Int]
parseCol range symbol = if symbol == 'L' 
    then (sliceRange 0 ((length range) `div` 2) range) 
    else (sliceRange ((length range) `div` 2) (length range) range)

parseColLine :: [Int] -> T.Text -> Int
parseColLine [elem] line = elem
parseColLine range text = case T.unpack text of
     [x] -> parseCol range x !! 0
     _ -> parseColLine (parseCol range (T.head text)) (T.drop 1 text)


seatId :: Int -> Int -> Int
seatId row col = (8 * row) + col

row :: T.Text -> Int
row str = parseRowLine [0..127] (slice 0 (T.length str - 4) str)

col :: T.Text -> Int
col str = parseColLine [0..7] (slice (T.length str - 3) (T.length str) str)

seatInfo :: T.Text -> [Int]
seatInfo str = [row str, col str, seatId (row str) (col str)]

valid :: [Int] -> Int -> Bool
valid seats x = (x - 1) `elem` seats && (x + 1) `elem` seats 

main = do
    ls <- fmap T.lines (Data.Text.IO.readFile "day5.txt")
    
    let seats = (map last (map seatInfo ls))
    
    -- part 1
    let max = maximum seats
    Prelude.putStrLn (show max)

    -- part 2

    let similar = (filter (valid seats) seats)
    Prelude.putStrLn (show (filter (\x -> not ( x `elem` similar)) [500..max-1]))
    return ()