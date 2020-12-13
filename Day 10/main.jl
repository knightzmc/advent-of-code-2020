file= open("./input.txt")
lines = readlines(file)
close(file)

nums = map(num -> parse(Int, num), lines)

struct Adapter
    data::Int64
    child::Union{Adapter, Nothing}
end 

function get_min_adapter_tree(inputs::Array{Int64}, next = 0)
    if isempty(inputs)
        return nothing
    end

    if next == 0
        append!(inputs, [maximum(inputs) + 3])
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

tree = get_min_adapter_tre(nums)

a, _, c = get_part1_output(tree)
println(a)
println(c)
println(a * c)