#include <iostream>
#include <fstream>

using namespace std;

struct TreeNode{
    int data;
    TreeNode* leftChild;
    TreeNode* rightChild;
    int height;
};

TreeNode* newTreeNode(int data);
int getHeight(TreeNode* root);

TreeNode* sortedArrayToTree(int a[], int start, int end);
TreeNode* unsortedArrayToTree(int a[], int idx, int length);

int getArraySize(TreeNode* root);
void populateArray(TreeNode* root, int a[], int idx, int arrSize);

void inOrder(TreeNode* root, ostream &output);
void print(int a[], int len);



const int NULL_VALUE = 0;



int main(){
    ifstream in = ifstream("input.txt");
    int ip;
    int fileSize = 0;

    //calculates how many ints are in the file
    while(in >> ip){
        fileSize++;
    }
    in.clear();
    in.seekg(0, ios::beg);

    int a[fileSize];
    for(int i = 0; i < fileSize; i++){
        in >> a[i];
    }

    TreeNode* root = 0;

    cout << "Original array: " << endl;
    print(a, fileSize);

    cout << "Converted to binary tree: " << endl;
    root = sortedArrayToTree(a, 0, fileSize - 1);
    inOrder(root, cout);
    cout << endl;

    cout << "Unsorted: " << endl;
    int arraySize = getArraySize(root);
    int unsorted[arraySize] = {NULL_VALUE};
    populateArray(root, unsorted, 0, arraySize);
    print(unsorted, arraySize);

    cout << "Unsorted to tree: " << endl;
    root = unsortedArrayToTree(unsorted, 0, arraySize);
    inOrder(root, cout);
    cout << endl;
    return 0;
}



TreeNode* newTreeNode(int data){
    TreeNode* ret = new TreeNode;
    ret->data = data;
    ret->leftChild = 0;
    ret->rightChild = 0;
    return ret;
}

int getHeight(TreeNode* root){
    int ret = -1;
    if(root){
        int left = getHeight(root->leftChild);
        int right = getHeight(root->rightChild);
        ret = max(left, right) + 1;
    }
    return ret;
}

TreeNode* sortedArrayToTree(int a[], int start, int end){
    TreeNode* ret = 0;
    if(start <= end){
        int mid = (start + end) / 2;
        ret = newTreeNode(a[mid]);
        ret->leftChild = sortedArrayToTree(a, start, mid - 1);
        ret->rightChild = sortedArrayToTree(a, mid + 1, end);
    }
    return ret;
}

TreeNode* unsortedArrayToTree(int a[], int idx, int length){
    TreeNode* ret = 0;
    if(a[idx] != NULL_VALUE){
        ret = newTreeNode(a[idx]);
        if(idx * 2 + 2 < length){
            ret->leftChild = unsortedArrayToTree(a, idx * 2 + 1, length);
            ret->rightChild = unsortedArrayToTree(a, idx * 2 + 2, length);
        }
    }
    return ret;
}


int getArraySize(TreeNode* root){
    int s = 1;
    int height = getHeight(root);

    if(root){
        // 2^(root->height + 1) - 1
        for(int i = 0; i < height + 1; i++){
            s *= 2;
        }
        s--;
    }
    return s;
}

void populateArray(TreeNode* root, int a[], int idx, int arrSize){
    if(root){
        a[idx] = root->data;
        if(2 * idx + 1 < arrSize){
            populateArray(root->leftChild, a, 2 * idx + 1, arrSize);
        }
        if(2 * idx + 2 < arrSize){
            populateArray(root->rightChild, a, 2 * idx + 2, arrSize);
        }
    }
}

void inOrder(TreeNode* root, ostream &output){
    if(root){
        inOrder(root->leftChild, output);

        output << "==========" << endl;
        output << "Data is: " << root->data << endl;
        if(root->leftChild){
            output << "Left child is " << root->leftChild->data << endl;
        }
        if(root->rightChild){
            output << "Right child is " << root->rightChild->data << endl;
        }
        output << "==========" << endl;

        //output << root->data << " ";

        inOrder(root->rightChild, output);
    }
}
void print(int a[], int len){
    for(int i = 0; i < len; i++){
        cout << a[i] << " ";
    }
    cout << endl;
}
