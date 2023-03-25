package io.bearch.webapi.utility.csv_processor;

import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

@Component
@Slf4j
public class CsvReader<T> {

    public List<T> readCsv(String fileName, Class<T> beanClass) throws IOException {
        HeaderColumnNameMappingStrategy<T> mappingStrategy = new HeaderColumnNameMappingStrategy<>();
        mappingStrategy.setType(beanClass);

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("data/" + fileName);
        Reader reader;
        try{
           reader =  new InputStreamReader(inputStream);
        }
        catch(NullPointerException e){
            throw new NullPointerException("Resource location is incorrect please check ./resources/data/ directory");
        }

        List<T> beans = new CsvToBeanBuilder<T>(reader)
                .withMappingStrategy(mappingStrategy)
                .withIgnoreLeadingWhiteSpace(true)
                .withSeparator('|')
                .build()
                .parse();

        reader.close();

        return beans;
    }

}
