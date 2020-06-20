package com.rafehi.methodsecuritytest.services;

import com.rafehi.methodsecuritytest.model.Item;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemRetriever {
  List<Item> items = List.of(new Item(123, "item one"));

  @PreAuthorize("denyAll()")
  public void deny(int id) {
  }

  @PreAuthorize("hasRole('LIST_ITEMS')")
  public List<Item> getItems() {
    return items;
  }

  @PreAuthorize("hasPermission(#id, @itemPermission.getTarget(), @itemPermission.read())")
  public Optional<Item> getItem(int id) {
    return items.stream().filter((item) -> item.getId() == id).findFirst();
  }
}
