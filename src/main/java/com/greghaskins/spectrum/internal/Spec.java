package com.greghaskins.spectrum.internal;

import com.greghaskins.spectrum.internal.blocks.NotifyingBlock;

import org.junit.runner.Description;
import org.junit.runner.notification.Failure;

final class Spec implements Child {

  private final NotifyingBlock block;
  private final Description description;
  private final Parent parent;
  private boolean ignored = false;

  Spec(final Description description, final NotifyingBlock block, final Parent parent) {
    this.description = description;
    this.block = block;
    this.parent = parent;
    this.ignored = parent.isIgnored();
  }

  @Override
  public Description getDescription() {
    return this.description;
  }

  @Override
  public void run(final RunReporting<Description, Failure> notifier) {
    if (this.ignored) {
      notifier.fireTestIgnored(this.description);
      return;
    }

    this.block.run(this.description, notifier);

  }

  @Override
  public int testCount() {
    return 1;
  }

  @Override
  public void focus() {
    if (this.ignored) {
      return;
    }

    this.parent.focus(this);
  }

  @Override
  public void ignore() {
    this.ignored = true;
  }

  @Override
  public boolean isAtomic() {
    return true;
  }

  @Override
  public boolean isLeaf() {
    return true;
  }

  @Override
  public boolean isEffectivelyIgnored() {
    return ignored;
  }
}
