#!/usr/bin/env python3
"""Small data check that needs only Python 3; it does not build the Android app."""
import heapq
import json
import math
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
DATA = json.loads((ROOT / "app/src/main/assets/campus_map.json").read_text())
NODES = {node["id"]: node for node in DATA["nodes"]}
PLACES = {place["id"]: place for place in DATA["places"]}


def shortest(start, destination):
    neighbours = {node_id: [] for node_id in NODES}
    for first, second in DATA["edges"]:
        a, b = NODES[first], NODES[second]
        length = math.hypot(a["x"] - b["x"], a["y"] - b["y"])
        neighbours[first].append((second, length))
        neighbours[second].append((first, length))
    distance, previous, queue = {start: 0.0}, {}, [(0.0, start)]
    while queue:
        cost, current = heapq.heappop(queue)
        if cost != distance[current]:
            continue
        if current == destination:
            break
        for next_node, length in neighbours[current]:
            new_cost = cost + length
            if new_cost < distance.get(next_node, float("inf")):
                distance[next_node] = new_cost
                previous[next_node] = current
                heapq.heappush(queue, (new_cost, next_node))
    if destination not in distance:
        return []
    path = [destination]
    while path[-1] != start:
        path.append(previous[path[-1]])
    return list(reversed(path))


assert len(NODES) == len(DATA["nodes"]), "duplicate node id"
assert len(PLACES) == len(DATA["places"]), "duplicate place id"
assert all(a in NODES and b in NODES for a, b in DATA["edges"]), "edge has missing node"
assert all(place["node"] in NODES for place in DATA["places"]), "place has missing node"
for start in DATA["places"]:
    for end in DATA["places"]:
        route = shortest(start["node"], end["node"])
        assert route, f"no path: {start['name']} to {end['name']}"
        assert route == list(reversed(shortest(end["node"], start["node"]))), "reverse mismatch"

uvpce = shortest(PLACES["uvpce_new"]["node"], PLACES["nescafe"]["node"])
mba = shortest(PLACES["mba"]["node"], PLACES["nescafe"]["node"])
assert uvpce != mba and uvpce[-1] == mba[-1] == "nescafe"
print(f"OK: {len(PLACES)} places, {len(NODES)} nodes, {len(DATA['edges'])} path segments")
print("UVPCE New -> Nescafe:", " -> ".join(uvpce))
print("MBA -> Nescafe:", " -> ".join(mba))
