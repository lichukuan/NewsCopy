package com.person.newscopy.common.util;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class KeywordFilteringUtil implements LifecycleObserver {

    private static class AcNode{
        AcNode fail;
        char data;
        HashMap<Character,AcNode> children =new HashMap<>();
        boolean isEnd;
        int size;
        AcNode(char data){
            this.data = data;
        }
    }

    public KeywordFilteringUtil(){
        root = new AcNode(' ');
    }

    private AcNode root = null;

    public void createAcTree(String[] data){
        if (data == null || data.length <= 0)return;
        //构建tri树
        for (int i = 0; i < data.length; i++) {
            char[] s = data[i].toCharArray();
            AcNode p = root;
            for (int j = 0; j < s.length; j++) {
                if(!p.children.containsKey(s[j])){
                    AcNode node = new AcNode(s[j]);
                    node.size = j+1;
                    p.children.put(s[j],node);
                    p = node;
                }else {
                    p = p.children.get(s[j]);
                }
            }
            p.isEnd = true;
        }
        //构建fail指针
        Queue<AcNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()){
            AcNode node = queue.remove();
            for (Character i : node.children.keySet()) {
                AcNode acNode = node.children.get(i);
                if (acNode == null)continue;
                if(node == root){
                    acNode.fail = root;
                }else {
                    AcNode ac = node.fail;
                    while(ac != null){
                        AcNode node1 = ac.children.get(acNode.data);
                        if (node1 != null){
                            acNode.fail = node1;
                            break;
                        }
                        ac = ac.fail;
                    }
                    if(ac == null)acNode.fail = root;
                }
                queue.add(acNode);
            }
        }
    }

    public List<String> match(String text){
        ArrayList<String> l = new ArrayList<>();
        if (text == null || "".equals(text))return l;
        char[] data = text.toCharArray();
        AcNode p = root;
        for (int i = 0; i < data.length; i++) {
            while (p.children.get(data[i]) == null && p != root){
                p = p.fail;
            }
            p = p.children.get(data[i]);
            if (p == null)p = root;
            AcNode temp = p;
            while (temp != root){
                if (temp.isEnd){
                    l.add(text.substring(i-temp.size+1,i+1));
                }
                temp = temp.fail;
            }
        }
        return l;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void clear(){
        for (Character character : root.children.keySet()) {
            root.children.put(character,null);
        }
        root = null;
    }
}
