/*
 *  Copyright (c) 2022 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in the
 * Software without restriction, including without limitation the rights to use, copy
 * modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and
 * to permit persons to whom the Software is furnished to do so, subject to the
 * following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * Not withstanding the foregoing, you may not use, copy, modify, merge, publish,
 * distribute, sublicense, create a derivative work, and/or sell copies of the Software in
 * any work that is designed, intended, or marketed for pedagogical or instructional
 * purposes related to programming, coding, application development, or information
 * technology.  Permission for such use, copying, modification, merger, publication,
 * distribution, sublicensing, creation of derivative works, or sale is expressly withheld.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE
 * USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */

package com.raywenderlich.fp

import com.raywenderlich.fp.lib.Chain2
import com.raywenderlich.fp.lib.State
import com.raywenderlich.fp.lib.invoke

fun <S, A, B, C> State<S, Pair<A, B>>.map2(
  fn: Chain2<A, B, C> // Fun2<A, B, C>
): State<S, C> =
  State { state ->
    val (pair, newState) = this(state) // Or st(state)
    val result = fn(pair.first)(pair.second) // Or fn(pair.first, pair.second)
    result to newState
  }

/** Applicative */
fun <S, T, R> State<S, T>.ap(
  fn: State<S, (T) -> R>
): State<S, R> =
  State { s0: S ->
    val (t, s1) = this(s0)
    val (fnValue, s2) = fn(s1)
    fnValue(t) to s2
  }


infix fun <S, A, B> State<S, (A) -> B>.appl(a: State<S, A>) = a.ap(this)
