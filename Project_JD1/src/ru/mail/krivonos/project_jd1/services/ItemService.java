package ru.mail.krivonos.project_jd1.services;

import ru.mail.krivonos.project_jd1.services.exceptions.ItemUniqueNumberException;
import ru.mail.krivonos.project_jd1.services.model.item.ItemDTO;
import ru.mail.krivonos.project_jd1.services.model.xml.XMLItemDTO;

import java.util.Collection;
import java.util.List;

public interface ItemService {

    void add(ItemDTO itemDTO) throws ItemUniqueNumberException;

    void deleteByID(Long id);

    List<ItemDTO> getAll(Integer pageNumber);

    Integer countPages();

    void addItems(Collection<XMLItemDTO> collection) throws ItemUniqueNumberException;
}
