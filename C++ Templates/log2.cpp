int log2u(int x) {
    if (x == 1) return 0;
    return 32 - __builtin_clz(x - 1);
}

int log2d(int x) {
    return 31 - __builtin_clz(x);
}
