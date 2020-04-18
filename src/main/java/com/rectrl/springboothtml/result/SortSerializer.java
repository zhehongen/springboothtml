package com.rectrl.springboothtml.result;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.data.domain.Sort;

import java.io.IOException;

/**
 * Page及其实现类PageImpl没有无参构造, 其超类Chunk也没有无参构造; 导致反序列化失败.-jackson(Sort也是如此)
 * 解决方案
 *  - 方案① 自定义序列化及反序列化Sort
 *  - 方案② 重写Sort
 *
 * @author hongen.zhang
 * time: 2019/11/20 16:57
 * email: hongen.zhang@things-matrix.com
 */
public class SortSerializer extends JsonSerializer<Sort> {

    @Override
    public void serialize(Sort sort, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(sort.toString());
    }
}
