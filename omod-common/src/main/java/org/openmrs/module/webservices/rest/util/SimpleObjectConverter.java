package org.openmrs.module.webservices.rest.util;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.openmrs.module.webservices.rest.SimpleObject;
import org.openmrs.module.webservices.rest.web.Hyperlink;

import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.converters.collections.AbstractCollectionConverter;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.mapper.Mapper;

/**
 * Custom XStream converter to serialize XML in REST services
 *
 */
public class SimpleObjectConverter extends AbstractCollectionConverter {

    public SimpleObjectConverter(Mapper mapper) {
		super(mapper);
	}

	public boolean canConvert(Class clazz) {
        return SimpleObject.class.isAssignableFrom(clazz);
    }

    public void marshal(Object value, HierarchicalStreamWriter writer, MarshallingContext context) {

		if (value instanceof Map) {
			Map<?, ?> map = (Map<?, ?>) value;
			for (Object obj : map.entrySet()) {
				Entry<?, ?> entry = (Entry<?, ?>) obj;
				writer.startNode(entry.getKey().toString());
				marshal(entry.getValue(), writer, context);
				writer.endNode();
			}
		} else if (value instanceof List) {
			List<?> list = (List<?>) value;
			for (Object obj : list) {
				marshal(obj, writer, context);
			}
		} else if (value instanceof Hyperlink) {
			writeItem(value, context, writer);
		} else {
			writer.setValue(value.toString());
		}

    }

    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
        return null;
    }

}
