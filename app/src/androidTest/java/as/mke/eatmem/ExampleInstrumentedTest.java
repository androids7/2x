package org.junit;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotates static fields that reference rules or methods that return them. A field must be public,
 * static, and a subtype of {@link org.junit.rules.TestRule}.  A method must be public static, and return
 * a subtype of {@link org.junit.rules.TestRule}.
 * <p>
 * The {@link org.junit.runners.model.Statement} passed
 * to the {@link org.junit.rules.TestRule} will run any {@link BeforeClass} methods,
 * then the entire body of the pager1 class (all contained methods, if it is
 * a standard JUnit pager1 class, or all contained classes, if it is a
 * {@link org.junit.runners.Suit