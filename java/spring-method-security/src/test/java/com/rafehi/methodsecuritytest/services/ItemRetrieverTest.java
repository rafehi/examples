package com.rafehi.methodsecuritytest.services;

import com.rafehi.methodsecuritytest.TestMethodSecurityConfiguration;
import com.rafehi.methodsecuritytest.model.Item;
import com.rafehi.methodsecuritytest.permission.ItemPermission;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Import({TestMethodSecurityConfiguration.class})
@SpringBootTest(classes= {ItemRetriever.class, ItemPermission.class})
@WithMockUser("test")
class ItemRetrieverTest {
  @Autowired
  ItemRetriever itemRetriever;

  @MockBean
  PermissionEvaluator proxyTest;

  @BeforeEach()
  public void setup() {
    when(proxyTest.hasPermission(any(), any(), any())).thenReturn(true);
    when(proxyTest.hasPermission(any(), any(), any(), any())).thenReturn(true);
  }

  @Test
  public void shouldThrowAccessDeniedException() {
    assertThrows(AccessDeniedException.class, () -> itemRetriever.deny(234));
  }

  @Test
  public void shouldReturnItem() {
    Optional<Item> item = itemRetriever.getItem(123);
    assertNotNull(item.get());
    assertEquals(123, item.get().getId());
  }

  @Test
  public void shouldThrowExceptionIfUserDoesNotHaveRole() {
    assertThrows(AccessDeniedException.class, () -> itemRetriever.getItems());
  }

  @Test
  @WithMockUser(roles = {"LIST_ITEMS"})
  public void shouldReturnItemsIfUserHasRole() {
    itemRetriever.getItems();
  }

  @Test
  public void shouldCallHasPermission() {
    itemRetriever.getItem(123);
    verify(proxyTest).hasPermission(any(),
        eq(123), eq(new ItemPermission().getTarget()), eq(new ItemPermission().read()));
  }
}