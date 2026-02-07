package com.tech_challenge.medical.domain;

import java.util.Collections;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class JoinRemovingOverlapCollector implements Collector<String, StringBuilder, String> {

    @Override
    public Supplier<StringBuilder> supplier() {
        return StringBuilder::new;
    }

    @Override
    public BiConsumer<StringBuilder, String> accumulator() {
        return (sb, str) -> {
            if (sb.length() == 0) {
                sb.append(str);
            } else {
                String currentText = sb.toString();
                int overlapIndex = findOverlapIndex(currentText, str);
                if (overlapIndex > 0) {
                    sb.append(str.substring(overlapIndex));
                } else {
                    sb.append(" ").append(str);
                }
            }
        };
    }

    private int findOverlapIndex(String currentText, String newText) {
        int maxOverlap = Math.min(currentText.length(), newText.length());
        for (int i = maxOverlap; i > 0; i--) {
            if (currentText.endsWith(newText.substring(0, i))) {
                return i;
            }
        }
        return 0;
    }

    @Override
    public BinaryOperator<StringBuilder> combiner() {
        return (sb1, sb2) -> {
            sb1.append(" ").append(sb2);
            return sb1;
        };
    }

    @Override
    public Function<StringBuilder, String> finisher() {
        return StringBuilder::toString;
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Collections.emptySet();
    }
}