package util;

import entities.enums.OrderStatus;
import org.hibernate.type.EnumType;

import java.util.Properties;

public class PostgreSQLEnumType extends EnumType<OrderStatus> {
    @Override
    public void setParameterValues(Properties parameters) {
        super.setParameterValues(parameters);
        parameters.put(EnumType.ENUM, OrderStatus.class.getName());
        parameters.put(EnumType.NAMED, true); // Use enum names instead of ordinal values
    }
}