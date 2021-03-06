# WildGeo 实时位置查询Android lib库

WildGeo是基于地理位存储和查询的Android library，它基于[wilddog](https://www.wilddog.com/)数据库，将位置信息存储数据到wilddog数据库中，可以实时查询指定地理位置的周边信息。

WildGeo有[Objective-C](https://github.com/WildDogTeam/lib-ios-wildgeo)
和[JavaScript](https://github.com/WildDogTeam/lib-js-wildgeo)客户端。

### 场景示例
假设你在开发一个点评酒吧的应用，在`/bars/<bar-id>`节点下存储酒吧相关的信息(名称，营业时间，价格范围)，你想增加一个查询附近酒吧的功能，WildGeo刚好适合。你可以用wildgeo存储地理信息，酒吧id作为健值，WildGeo
可以查询附近的酒吧Id,然后再获取酒吧的相关信息。

### 整合WildGeo到android项目
添加wildgeo依赖到`gradle.build`

```groovy
dependencies {
    compile 'com.wilddog:wildgeo:2.0.0+'
}
```

### 入门

WildGeo使用野狗数据库，你可以从这里
[注册](https://www.wilddog.com/my-account/signup)一个免费体验账号。

### 初始化WildGeo
如果应用中还没有初始化过Wilddog Sync，那么应该先进行初始化，参考文档：[https://docs.wilddog.com/sync/Android/quickstart.html](https://docs.wilddog.com/sync/Android/quickstart.html)

WildGeo对象用于数据的存储和查询，为了创建一个`WildGeo`实例，你需要先创建一个Wilddog的引用。

```java
SyncReference ref = WilddogSync.getInstance().getReference("/path/to/geodata");
WildGeo wildGeo = new WildGeo(ref);
```
记得添加安全规则

#### 设置位置数据

WildGeo中你可以根据key值查询和设置地理位置，通过传入key和`GeoLocation`(latitude,longitude)参数调用`setLocation`方法:

```java
wildgeo.setLocation("wilddog-hq", new GeoLocation(37.7853889, -122.4056973));
```

检测是否成功保存数据到服务器，可以添加`wildgeo.CompletionListener` 方法:

```java
wildgeo.setLocation("wilddog-hq", new GeoLocation(37.7853889, -122.4056973), new WildGeo.CompletionListener() {
    @Override
    public void onComplete(String key, SyncError error) {
        if (error != null) {
            Log.d(TAG, "There was an error saving the location to wildgeo: " + error);
        } else {
            Log.d(TAG, "Location saved on server successfully!");
        }
    }
});
```

删除位置信息 `removeLocation`方法:

```java
wildgeo.removeLocation("wilddog-hq");
```

#### 获取位置信息

```java
wildgeo.getLocation("wilddog-hq", new LocationCallback() {
    @Override
    public void onLocationResult(String key, GeoLocation location) {
        if (location != null) {
            Log.d(TAG, String.format("The location for key %s is [%f,%f]", key, location.latitude, location.longitude));
        } else {
            Log.d(TAG, String.format("There is no location for key %s in wildgeo", key));
        }
    }

    @Override
    public void onCancelled(SyncError syncError) {
        Log.d(TAG, "There was an error getting the wildgeo location: " + syncError);
    }
});
```

### Geo查询

WildGeo能够查询指定地理区域的所有keys，实时更新数据和触发相关联的事件。
`GeoQuery`可以改变中心区域大小。

```java
// creates a new query around [37.7832, -122.4056] with a radius of 0.6 kilometers
GeoQuery geoQuery = wildgeo.queryAtLocation(new GeoLocation(37.7832, -122.4056), 0.6);
```

#### geo queries 事件

geo query可能触发5种事件:

1. **Key Entered**: 符合查询条件的location key.
2. **Key Exited**: 不再符合查询条件的location key.
3. **Key Moved**:  符合查询条件的location key变化时.
4. **Query Ready**: 所有数据加载完成和事件触发完成.
5. **Query Error**: 执行查询出错时触发.

`GeoQueryEventListener`:

```java
geoQuery.addGeoQueryEventListener(new GeoQueryEventListener() {
    @Override
    public void onKeyEntered(String key, GeoLocation location) {
        Log.d(TAG, String.format("Key %s entered the search area at [%f,%f]", key, location.latitude, location.longitude));
    }

    @Override
    public void onKeyExited(String key) {
        Log.d(TAG, String.format("Key %s is no longer in the search area", key));
    }

    @Override
    public void onKeyMoved(String key, GeoLocation location) {
        Log.d(TAG, String.format("Key %s moved within the search area to [%f,%f]", key, location.latitude, location.longitude));
    }

    @Override
    public void onGeoQueryReady() {
        Log.d(TAG, "All initial data has been loaded and events have been fired!");
    }

    @Override
    public void onGeoQueryError(SyncError error) {
        Log.d(TAG, "There was an error with this query: " + error);
    }
});
```
`removeGeoQueryEventListener`和`removeAllListeners`可以移除`GeoQuery`的监听事件。

#### Updating the query criteria

`GeoQuery`查询区域可以通过`setCenter` 和 `setRadius`改变，在变化过程中Key exited 和 key entered events将会被触发，但是移动事件是独立触发的。
当用户的视角切换后，更新查询到可见区域是很有用的。

### 版本升级
从2.0.0版本开始，WildGeo使用2.x版本的Wilddog Sync。如果应用中在使用旧版本的wilddog，可以使用[1.x版本的wildgeo](https://github.com/WildDogTeam/lib-android-wildgeo/tree/v1).  
从2.0.0版本开始，WildGeo不再支持非Android环境。


### 更多示例

这里分类汇总了 WildDog平台上的示例程序和开源应用，　链接地址：[https://github.com/WildDogTeam/wilddog-demos](https://github.com/WildDogTeam/wilddog-demos)


### License
MIT
http://wilddog.mit-license.org/

### 感谢 Thanks

We would like to thank the following projects for helping us achieve our goals:

Open Source:
   
* [geofire-java](https://github.com/firebase/geofire-java) Realtime location queries with Firebase








