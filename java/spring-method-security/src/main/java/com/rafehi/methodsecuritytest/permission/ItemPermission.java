package com.rafehi.methodsecuritytest.permission;

public class ItemPermission {
  public String getTarget() { return "item"; }

  public String write() {
    return "write";
  }

  public String read() {
    return "read";
  }
}
