/*
 * Created on Oct 24, 2010
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 * 
 * Copyright @2010-2011 the original author or authors.
 */
package org.assertj.core.internal.bigdecimals;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TEN;
import static org.assertj.core.data.Offset.offset;
import static org.assertj.core.error.ShouldBeEqualWithinOffset.shouldBeEqual;
import static org.assertj.core.test.TestData.someInfo;
import static org.assertj.core.test.TestFailures.failBecauseExpectedAssertionErrorWasNotThrown;
import static org.assertj.core.util.FailureMessages.actualIsNull;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;

import org.junit.Test;

import org.assertj.core.api.AssertionInfo;
import org.assertj.core.internal.BigDecimalsBaseTest;


/**
 * Tests for <code>{@link org.assertj.core.internal.BigDecimals#assertIsCloseTo(org.assertj.core.api.AssertionInfo, java.math.BigDecimal, java.math.BigDecimal, org.assertj.core.data.Offset)}</code>.
 *
 * @author Joel Costigliola
 */
public class BigDecimals_assertIsCloseTo_Test extends BigDecimalsBaseTest {

  @Test
  public void should_fail_if_actual_is_null() {
    thrown.expectAssertionError(actualIsNull());
    bigDecimals.assertIsCloseTo(someInfo(), null, ONE, offset(ONE));
  }

  @Test
  public void should_pass_if_big_decimals_difference_is_less_than_given_offset() {
    bigDecimals.assertIsCloseTo(someInfo(), new BigDecimal("5.0"), new BigDecimal("5.1"), offset(ONE));
  }

  @Test
  public void should_pass_if_big_decimals_difference_is_equal_to_given_offset() {
    bigDecimals.assertIsCloseTo(someInfo(), new BigDecimal("5.0"), new BigDecimal("6.0"), offset(ONE));
  }

  @Test
  public void should_fail_if_big_decimals_difference_is_greater_than_offset() {
    AssertionInfo info = someInfo();
    try {
      bigDecimals.assertIsCloseTo(info, TEN, ONE, offset(ONE));
    } catch (AssertionError e) {
      verify(failures).failure(info, shouldBeEqual(TEN, ONE, offset(ONE), TEN.subtract(ONE)));
      return;
    }
    failBecauseExpectedAssertionErrorWasNotThrown();
  }

  @Test
  public void should_fail_if_actual_is_null_whatever_custom_comparison_strategy_is() {
    thrown.expectAssertionError(actualIsNull());
    bigDecimalsWithAbsValueComparisonStrategy.assertIsCloseTo(someInfo(), null, ONE, offset(ONE));
  }

  @Test
  public void should_pass_if_big_decimals_are_equal_by_comparison_whatever_custom_comparison_strategy_is() {
    bigDecimalsWithAbsValueComparisonStrategy.assertIsCloseTo(someInfo(), new BigDecimal("5.0"), new BigDecimal("5"), offset(ONE));
  }

  @Test
  public void should_fail_if_big_decimals_are_not_equal_by_comparison_whatever_custom_comparison_strategy_is() {
    AssertionInfo info = someInfo();
    try {
      bigDecimalsWithAbsValueComparisonStrategy.assertIsCloseTo(info, TEN, ONE, offset(ONE));
    } catch (AssertionError e) {
      verify(failures).failure(info, shouldBeEqual(TEN, ONE, offset(ONE), TEN.subtract(ONE)));
      return;
    }
    failBecauseExpectedAssertionErrorWasNotThrown();
  }
}
