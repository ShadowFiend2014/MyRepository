// ISecurityCenter.aidl
package com.test.myapplication;

/**
 * 远程加解密接口
 */
interface ISecurityCenter {
    String encrypt(String content);
    String decrypt(String password);
}