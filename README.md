# ScrollConflictSamples
滑动冲突实例

# 解决方式
内部和外部拦截两种方式解决滑动冲突。

VerContainerView和VerContainerView2 均有一个Scroller类来做内容的滑动，类似竖向的ViewPager， 其内部均存在一个Listview；
这里就会存在竖向的滑动冲突。

#### 内部拦截法：ListView滑动到顶部时，不需要松手继续下拉即可触发VerContainerView的Scroller;

#### 外部拦截法：ListView滑动到顶部时，必须松手下拉才能触发VerContainerView2的Scroller。

> 横向滑动冲突解决方式类似，理解思想后很好解决。
