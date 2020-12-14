file= open("./input.txt")
lines = readlines(file)
close(file)

nums = map(num -> parse(UInt8, num), lines)

struct Adapter
    data::UInt8
    child::Union{Adapter, Nothing}
end 

function get_min_adapter_tree(inputs::Array{UInt8}, next = 0)
    if isempty(inputs)
        return nothing
    end

    if next == 0
        append!(inputs, maximum(inputs) + 3)
    end
    
    valid = filter(num -> num - next <= 3, inputs)
    nextvalid = minimum(valid)

    return Adapter(nextvalid, get_min_adapter_tree(filter(i -> i != nextvalid, inputs), nextvalid))
end



function get_part1_output(elem)
    sums = [0, 0, 0]
    prev = 0
    curr = elem
    while curr != nothing
        diff = curr.data - prev
        sums[diff] += 1
        prev = curr.data
        curr = curr.child
    end
    return (sums[1], sums[2], sums[3])
end

tree = get_min_adapter_tree(nums)

a, _, c = get_part1_output(tree)

println(a * c)

#Part 2

struct Tree
    data::UInt8
    children::Array{Tree}
end


children_memoiser = Dict{Int, Tree}()
function get_all_adapter_tree(inputs::Array{UInt8}, next = 0)

    valid = filter(num -> (num - next <= 3) && (num - next > 0), inputs)

    children = map(num -> begin
            get!(children_memoiser, num) do
                newinputs = filter(i -> i != num, inputs)
                get_all_adapter_tree(newinputs, num)
            end
    end, valid)


    return Tree(next, children)
end

tree = get_all_adapter_tree(nums)

combo_memoiser = Dict{Tree, Int}()
function get_combinations(tree, max)
    if isempty(tree.children)
        if tree.data == max
            return 1
        else
             return 0
        end
    end
    
    matching = 0

    for child in tree.children
        child_combos = get!(combo_memoiser, child) do
            get_combinations(child, max)
        end
        matching += child_combos
    end
    
    return matching
end

println(get_combinations(tree, maximum(nums)))