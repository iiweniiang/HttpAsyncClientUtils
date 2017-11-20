# HttpAsyncClientUtils

util for httpAsyncClient and httpSyncClient  in JAVA

这两天研究了一下异步的httpclient ---- httpAsyncClient

原来使用httpclient都是同步的,如果项目中有大量的httpclient的话,可能会造成阻塞,如果使用异步请求的话可以避免这些问题

可以用在调用第三方接口或者不需要知道请求返回结果的场景下

于是写了一个工具类来封装了同步异步httpclient
