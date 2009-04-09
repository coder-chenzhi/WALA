/*******************************************************************************
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * This file is a derivative of code released by the University of
 * California under the terms listed below.  
 *
 * Refinement Analysis Tools is Copyright (c) 2007 The Regents of the
 * University of California (Regents). Provided that this notice and
 * the following two paragraphs are included in any distribution of
 * Refinement Analysis Tools or its derivative work, Regents agrees
 * not to assert any of Regents' copyright rights in Refinement
 * Analysis Tools against recipient for recipient's reproduction,
 * preparation of derivative works, public display, public
 * performance, distribution or sublicensing of Refinement Analysis
 * Tools and derivative works, in source code and object code form.
 * This agreement not to assert does not confer, by implication,
 * estoppel, or otherwise any license or rights in any intellectual
 * property of Regents, including, but not limited to, any patents
 * of Regents or Regents' employees.
 * 
 * IN NO EVENT SHALL REGENTS BE LIABLE TO ANY PARTY FOR DIRECT,
 * INDIRECT, SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES,
 * INCLUDING LOST PROFITS, ARISING OUT OF THE USE OF THIS SOFTWARE
 * AND ITS DOCUMENTATION, EVEN IF REGENTS HAS BEEN ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *   
 * REGENTS SPECIFICALLY DISCLAIMS ANY WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
 * FOR A PARTICULAR PURPOSE AND FURTHER DISCLAIMS ANY STATUTORY
 * WARRANTY OF NON-INFRINGEMENT. THE SOFTWARE AND ACCOMPANYING
 * DOCUMENTATION, IF ANY, PROVIDED HEREUNDER IS PROVIDED "AS
 * IS". REGENTS HAS NO OBLIGATION TO PROVIDE MAINTENANCE, SUPPORT,
 * UPDATES, ENHANCEMENTS, OR MODIFICATIONS.
 */
package com.ibm.wala.core.tests.demandpa;

import java.io.IOException;

import com.ibm.wala.demandpa.alg.DemandRefinementPointsTo;
import com.ibm.wala.demandpa.alg.refinepolicy.AlwaysRefineFieldsPolicy;
import com.ibm.wala.demandpa.alg.refinepolicy.NeverRefineCGPolicy;
import com.ibm.wala.demandpa.alg.refinepolicy.SinglePassRefinementPolicy;
import com.ibm.wala.eclipse.util.CancelException;
import com.ibm.wala.ipa.cha.ClassHierarchyException;

public class RefineFieldsPtrTest extends AbstractPtrTest {

  @Override
  protected void setUp() throws Exception {
  }

  public void testNastyPtrs() throws ClassHierarchyException, IllegalArgumentException, CancelException, IOException {
    doPointsToSizeTest(TestInfo.SCOPE_FILE, TestInfo.TEST_NASTY_PTRS, 10);
  }

  public void testGlobal() throws ClassHierarchyException, IllegalArgumentException, CancelException, IOException {
    doPointsToSizeTest(TestInfo.SCOPE_FILE, TestInfo.TEST_GLOBAL, 1);
  }

  public void testFields() throws ClassHierarchyException, IllegalArgumentException, CancelException, IOException {
    doPointsToSizeTest(TestInfo.SCOPE_FILE, TestInfo.TEST_FIELDS, 1);
  }

  public void testFieldsHarder() throws ClassHierarchyException, IllegalArgumentException, CancelException, IOException {
    doPointsToSizeTest(TestInfo.SCOPE_FILE, TestInfo.TEST_FIELDS_HARDER, 1);
  }

  public void testArrays() throws ClassHierarchyException, IllegalArgumentException, CancelException, IOException {
    doPointsToSizeTest(TestInfo.SCOPE_FILE, TestInfo.TEST_ARRAYS, 2);
  }

  public void testGetterSetter() throws ClassHierarchyException, IllegalArgumentException, CancelException, IOException {
    doPointsToSizeTest(TestInfo.SCOPE_FILE, TestInfo.TEST_GETTER_SETTER, 1);
  }

  public void testArraySet() throws ClassHierarchyException, IllegalArgumentException, CancelException, IOException {
    doPointsToSizeTest(TestInfo.SCOPE_FILE, TestInfo.TEST_ARRAY_SET, 2);
  }

  public void testArraySetIter() throws ClassHierarchyException, IllegalArgumentException, CancelException, IOException {
    doPointsToSizeTest(TestInfo.SCOPE_FILE, TestInfo.TEST_ARRAY_SET_ITER, 2);
  }

  public void testMultiDim() throws ClassHierarchyException, IllegalArgumentException, CancelException, IOException {
    doPointsToSizeTest(TestInfo.SCOPE_FILE, TestInfo.TEST_MULTI_DIM, 2);
  }

  @Override
  public DemandRefinementPointsTo makeDemandPointerAnalysis(String scopeFile, String mainClass) throws ClassHierarchyException,
      IllegalArgumentException, CancelException, IOException {
    DemandRefinementPointsTo dmp = super.makeDemandPointerAnalysis(scopeFile, mainClass);
    dmp
        .setRefinementPolicyFactory(new SinglePassRefinementPolicy.Factory(new AlwaysRefineFieldsPolicy(),
            new NeverRefineCGPolicy()));
    return dmp;
  }

}
