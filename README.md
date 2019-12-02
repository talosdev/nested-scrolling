This is a sample project used to demonstrate an undesired behavior when combining nested
recyclerviews and an auto-hiding Bottom bar.
In a vertical recycler view, some items can be a nested horizontal recycler view. If we initiate
a vertical scroll gesture starting from the horizontal recycler view, the vertical recycler view
scrolls, but the scroll gesture doesn't reach the bottom bar so the hide/show functionality of the
bottom bar is not triggered.