// IBinderPool.aidl
package com.test.myapplication;

/**
 * Binder 连接池，用于提供查询 Service 所提供的所有远程接口
 */
interface IBinderPool {
    IBinder queryBinder(int binderCode);
}