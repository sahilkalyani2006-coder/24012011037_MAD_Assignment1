#!/usr/bin/env python3
"""Render the same JSON used by Android. Matplotlib is needed only for this preview."""
import json
from pathlib import Path
import matplotlib.pyplot as plt
from matplotlib.patches import Polygon

from validate_map import DATA, NODES, PLACES, shortest

ROOT = Path(__file__).resolve().parents[1]
COLORS = {"field": "#c1daa7", "court": "#b8cdc0", "pool": "#6ac4dc", "building": "#dacdb4"}


def render(start_id, end_id, filename):
    route = shortest(PLACES[start_id]["node"], PLACES[end_id]["node"])
    fig, ax = plt.subplots(figsize=(8, 8), dpi=160)
    ax.set_facecolor("#eff4ea")
    for shape in DATA["shapes"]:
        ax.add_patch(Polygon(shape["points"], facecolor=COLORS[shape["kind"]], edgecolor="#828b7d", linewidth=.7))
    for first, second in DATA["edges"]:
        a, b = NODES[first], NODES[second]
        ax.plot([a["x"], b["x"]], [a["y"], b["y"]], color="#b8beb9", linewidth=5, solid_capstyle="round")
        ax.plot([a["x"], b["x"]], [a["y"], b["y"]], color="white", linewidth=1.7, solid_capstyle="round")
    for first, second in zip(route, route[1:]):
        a, b = NODES[first], NODES[second]
        ax.plot([a["x"], b["x"]], [a["y"], b["y"]], color="#2174d0", linewidth=4, solid_capstyle="round")
    for index, place in enumerate(DATA["places"], 1):
        node = NODES[place["node"]]
        chosen = place["id"] in (start_id, end_id)
        ax.scatter(node["x"], node["y"], s=55 if chosen else 24, color="#e04b43" if chosen else "#305b7b", zorder=5)
        ax.text(node["x"], node["y"], str(index), color="white", fontsize=5, ha="center", va="center", zorder=6)
    ax.set_xlim(0, DATA["width"]); ax.set_ylim(DATA["height"], 0); ax.set_aspect("equal"); ax.axis("off")
    ax.set_title(f"{PLACES[start_id]['name']} → {PLACES[end_id]['name']}", fontsize=10)
    fig.tight_layout(); fig.savefig(ROOT / "docs" / filename, bbox_inches="tight"); plt.close(fig)


render("uvpce_new", "nescafe", "preview_uvpce_to_nescafe.png")
render("mba", "nescafe", "preview_mba_to_nescafe.png")
print("Wrote two previews in docs/")
