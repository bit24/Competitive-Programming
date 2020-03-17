template<class T, class Compare = less<T>>
struct lazyPriorityQueue {
    priority_queue<T, vector<T>, Compare> q;
    priority_queue<T, vector<T>, Compare> r;

    void push(T x) {
        q.push(x);
    }

    void erase(T x) {
        r.push(x);
    }

    void clearLazy() {
        while (q.size() && r.size() && q.top() == r.top()) {
            q.pop();
            r.pop();
        }
    }

    T top() {
        clearLazy();
        return q.top();
    }

    void pop() {
        clearLazy();
        q.pop();
    }

    int size() {
        return q.size() - r.size();
    }
};
