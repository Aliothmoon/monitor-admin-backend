package com.swust.aliothmoon.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import lombok.experimental.UtilityClass;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;

import static java.util.function.Function.identity;

/**
 * stream 流工具类
 *
 * @author Czy
 */
@UtilityClass
public class StreamUtils {

    /**
     * 将collection过滤
     *
     * @param collection 需要转化的集合
     * @param function   过滤方法
     * @return 过滤后的list
     */
    public static <E> List<E> filter(Collection<E> collection, Predicate<E> function) {
        return filter(collection, function, identity());
    }

    public static <E, R> List<R> filter(Collection<E> collection, Predicate<E> predicate, Function<E, R> mapping) {
        ArrayList<R> es = new ArrayList<>();
        if (CollUtil.isEmpty(collection)) {
            return es;
        }
        for (E e : collection) {
            if (predicate.test(e)) {
                es.add(mapping.apply(e));
            }
        }
        return es;
    }

    public static <E, R> List<R> filterSpreading(Collection<E> collection, Predicate<R> predicate, Function<E, R> mapping) {
        ArrayList<R> es = new ArrayList<>();
        if (CollUtil.isEmpty(collection)) {
            return es;
        }
        for (E e : collection) {
            R apply = mapping.apply(e);
            if (predicate.test(apply)) {
                es.add(apply);
            }
        }
        return es;
    }

    /**
     * 将collection排序
     *
     * @param collection 需要转化的集合
     * @param comparing  排序方法
     * @return 排序后的list
     */
    public static <E> List<E> sorted(Collection<E> collection, Comparator<E> comparing) {
        if (CollUtil.isEmpty(collection)) {
            return new ArrayList<>();
        }
        ArrayList<E> es = new ArrayList<>(collection);
        es.sort(comparing);
        return es;
    }

    /**
     * 将collection转化为类型不变的map<br>
     * <B>{@code Collection<V>  ---->  Map<K,V>}</B>
     *
     * @param collection 需要转化的集合
     * @param key        V类型转化为K类型的lambda方法
     * @param <V>        collection中的泛型
     * @param <K>        map中的key类型
     * @return 转化后的map
     */
    public static <V, K> Map<K, V> toIdentityMap(Collection<V> collection, Function<V, K> key) {
        if (CollUtil.isEmpty(collection)) {
            return new HashMap<>();
        }
        return collection.stream().filter(Objects::nonNull)
                .collect(Collectors.toMap(key, identity(), (l, r) -> l));
    }

    /**
     * 将Collection转化为map(value类型与collection的泛型不同)<br>
     * <B>{@code Collection<E> -----> Map<K,V>  }</B>
     *
     * @param collection 需要转化的集合
     * @param key        E类型转化为K类型的lambda方法
     * @param value      E类型转化为V类型的lambda方法
     * @param <E>        collection中的泛型
     * @param <K>        map中的key类型
     * @param <V>        map中的value类型
     * @return 转化后的map
     */
    public static <E, K, V> Map<K, V> toMap(Collection<E> collection, Function<E, K> key, Function<E, V> value) {
        HashMap<K, V> result = new HashMap<>();
        if (CollUtil.isEmpty(collection)) {
            return result;
        }

        for (E e : collection) {
            if (e != null) {
                result.put(key.apply(e), value.apply(e));
            }
        }
        return result;
    }

    public static <E, K, V> Map<K, E> toMap(Collection<E> collection, Function<E, K> key) {
        return toMap(collection, key, identity());
    }

    public static <E, K, V> Map<K, V> mapping(Collection<E> col, Map<K, V> origin, Function<E, K> key, Function<E, V> value) {
        if (CollUtil.isEmpty(col)) {
            return origin;
        }

        for (E e : col) {
            if (e != null) {
                origin.put(key.apply(e), value.apply(e));
            }
        }
        return origin;
    }

    public static <E, K> Map<K, E> mapping(Collection<E> col, Map<K, E> origin, Function<E, K> key) {
        return StreamUtils.mapping(col, origin, key, identity());
    }

    public static <E, K> List<E> toUniqueList(Collection<E> col, Function<E, K> key) {
        HashMap<K, E> tmp = new HashMap<>();
        if (CollUtil.isEmpty(col)) {
            return new ArrayList<>();
        }

        for (E e : col) {
            if (e != null) {
                tmp.putIfAbsent(key.apply(e), e);
            }
        }
        return new ArrayList<>(tmp.values());
    }

    /**
     * 将collection按照规则(比如有相同的班级id)分类成map<br>
     * <B>{@code Collection<E> -------> Map<K,List<E>> } </B>
     *
     * @param collection 需要分类的集合
     * @param key        分类的规则
     * @param <E>        collection中的泛型
     * @param <K>        map中的key类型
     * @return 分类后的map
     */
    public static <E, K> Map<K, List<E>> groupByKey(Collection<E> collection, Function<E, K> key) {
        return groupByKey(collection, key, identity());
    }

    public static <E, K, V> Map<K, List<V>> groupByKey(Collection<E> collection, Function<E, K> key, Function<E, V> val) {
        LinkedHashMap<K, List<V>> result = new LinkedHashMap<>();
        if (CollUtil.isEmpty(collection)) {
            return result;
        }
        for (E e : collection) {
            if (e != null) {
                result.computeIfAbsent(key.apply(e), k -> new ArrayList<>()).add(val.apply(e));
            }
        }
        return result;
    }

    /**
     * 将collection按照两个规则(比如有相同的年级id,班级id)分类成双层map<br>
     * <B>{@code Collection<E>  --->  Map<T,Map<U,List<E>>> } </B>
     *
     * @param collection 需要分类的集合
     * @param key1       第一个分类的规则
     * @param key2       第二个分类的规则
     * @param <E>        集合元素类型
     * @param <K>        第一个map中的key类型
     * @param <U>        第二个map中的key类型
     * @return 分类后的map
     */
    public static <E, K, U> Map<K, Map<U, List<E>>> groupBy2Key(Collection<E> collection, Function<E, K> key1,
                                                                Function<E, U> key2) {
        if (CollUtil.isEmpty(collection)) {
            return new HashMap<>();
        }
        return collection.stream().filter(Objects::nonNull)
                .collect(Collectors.groupingBy(key1, LinkedHashMap::new,
                        Collectors.groupingBy(key2, LinkedHashMap::new, Collectors.toList())));
    }

    /**
     * 将collection按照两个规则(比如有相同的年级id,班级id)分类成双层map<br>
     * <B>{@code Collection<E>  --->  Map<T,Map<U,E>> } </B>
     *
     * @param collection 需要分类的集合
     * @param key1       第一个分类的规则
     * @param key2       第二个分类的规则
     * @param <T>        第一个map中的key类型
     * @param <U>        第二个map中的key类型
     * @param <E>        collection中的泛型
     * @return 分类后的map
     */
    public static <E, T, U> Map<T, Map<U, E>> group2Map(Collection<E> collection, Function<E, T> key1,
                                                        Function<E, U> key2) {
        if (CollUtil.isEmpty(collection) || key1 == null || key2 == null) {
            return new HashMap<>();
        }
        return collection.stream().filter(Objects::nonNull).collect(
                Collectors.groupingBy(key1, LinkedHashMap::new, Collectors.toMap(key2, identity(), (l, r) -> l)));
    }

    /**
     * 将collection转化为List集合，但是两者的泛型不同<br>
     * <B>{@code Collection<E>  ------>  List<T> } </B>
     *
     * @param map      需要转化的集合
     * @param function collection中的泛型转化为list泛型的lambda表达式
     * @param <T>      List中的泛型
     * @return 转化后的list
     */
    public static <K, V, T> List<T> toList(Map<K, V> map, BiFunction<K, V, T> function) {
        if (CollUtil.isEmpty(map)) {
            return new ArrayList<>();
        }
        List<T> list = new ArrayList<>();
        map.forEach((k, v) -> list.add(function.apply(k, v)));
        return list;
    }

    /**
     * 将collection转化为List集合，但是两者的泛型不同<br>
     * <B>{@code Collection<E>  ------>  List<T> } </B>
     *
     * @param collection 需要转化的集合
     * @param function   collection中的泛型转化为list泛型的lambda表达式
     * @param <E>        collection中的泛型
     * @param <T>        List中的泛型
     * @return 转化后的list
     */
    public static <E, T> List<T> toList(Collection<E> collection, Function<E, T> function) {
        if (CollUtil.isEmpty(collection)) {
            return new ArrayList<>();
        }
        ArrayList<T> list = new ArrayList<>(collection.size());
        for (E element : collection) {
            T t;
            if (element != null && (t = function.apply(element)) != null) {
                list.add(t);
            }
        }
        return list;
    }

    public static <E, T> List<T> toListSupplier(Collection<E> collection, Supplier<T> supplier, BiConsumer<E, T> consumer) {
        if (CollUtil.isEmpty(collection)) {
            return new ArrayList<>();
        }
        ArrayList<T> list = new ArrayList<>(collection.size());
        for (E element : collection) {
            if (element != null) {
                T t = supplier.get();
                consumer.accept(element, t);
                list.add(t);
            }
        }
        return list;
    }


    /**
     * 将collection转化为Set集合，但是两者的泛型不同<br>
     * <B>{@code Collection<E>  ------>  Set<T> } </B>
     *
     * @param collection 需要转化的集合
     * @param function   collection中的泛型转化为set泛型的lambda表达式
     * @param <E>        collection中的泛型
     * @param <T>        Set中的泛型
     * @return 转化后的Set
     */
    public static <E, T> Set<T> toSet(Collection<E> collection, Function<E, T> function) {
        HashSet<T> ret = new HashSet<>();
        if (CollUtil.isEmpty(collection) || function == null) {
            return ret;
        }

        for (E e : collection) {
            if (e != null) {
                ret.add(function.apply(e));
            }
        }
        return ret;
    }

    /**
     * 合并两个相同key类型的map
     *
     * @param first  第一个需要合并的 map
     * @param second 第二个需要合并的 map
     * @param merge  合并的lambda，将key  value1 value2合并成最终的类型,注意value可能为空的情况
     * @param <K>    map中的key类型
     * @param <X>    第一个 map的value类型
     * @param <Y>    第二个 map的value类型
     * @param <V>    最终map的value类型
     * @return 合并后的map
     */
    public static <K, X, Y, V> Map<K, V> merge(Map<K, X> first, Map<K, Y> second, BiFunction<X, Y, V> merge) {
        first = Optional.ofNullable(first).orElseGet(Collections::emptyMap);
        second = Optional.ofNullable(second).orElseGet(Collections::emptyMap);
        Set<K> key = new HashSet<>();
        key.addAll(first.keySet());
        key.addAll(second.keySet());
        Map<K, V> map = new HashMap<>();
        for (K t : key) {
            V z = merge.apply(first.get(t), second.get(t));
            if (z != null) {
                map.put(t, z);
            }
        }
        return map;
    }

    /**
     * 计算总和
     *
     * @return 计算结果
     */
    public static <T> long longSum(Collection<T> list, Function<T, ? extends Long> function) {
        if (CollectionUtil.isEmpty(list) || Objects.isNull(function)) {
            return 0L;
        }
        long sum = 0L;
        for (T t : list) {
            sum += function.apply(t);
        }
        return sum;
    }

    /**
     * 计算总和
     *
     * @return 计算结果
     */
    public static <T> int intSum(Collection<T> list, Function<T, ? extends Integer> function) {

        if (CollUtil.isEmpty(list) || Objects.isNull(function)) {
            return 0;
        }
        int sum = 0;
        for (T t : list) {
            sum += function.apply(t);
        }
        return sum;
    }
}

