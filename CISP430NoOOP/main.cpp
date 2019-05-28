#include <iostream>
#include "Heap.h"
#include "TreeNode.h"
#include "./include/Stack.h"

int useQueue();
int useLinkedList();
int useAvlTree();
int useFindPath();
int useSearchSort();
int useString();
int usePostfix();

using namespace std;


int main()
{
    //useStack();
    //useQueue();
    //useLinkedList();
    //useAvlTree();
    //useFindPath();
    //useSearchSort();
    //Heap<int>::test();
    //useString();
    //Stack<int>::test();
    //usePostfix();
    TreeNode<int>::test();
    return 0;
}
