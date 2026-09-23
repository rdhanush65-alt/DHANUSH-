# Simple recommendation demo.
# Replace this rule-based logic with a trained ML model later.

def recommend(products, purchased_categories):
    return [
        p for p in products
        if p.get("category") in purchased_categories
    ][:5]
