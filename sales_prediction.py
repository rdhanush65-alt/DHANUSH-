# Simple demand prediction demo.
# A real project can replace this with Linear Regression,
# Random Forest, XGBoost, or a time-series model.

def predict_demand(previous_sales):
    if not previous_sales:
        return 0
    return round(sum(previous_sales[-7:]) / min(7, len(previous_sales)))
