// ============================================================================
// Copyright BRAINTRIBE TECHNOLOGY GMBH, Austria, 2002-2022
// 
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
// 
//     http://www.apache.org/licenses/LICENSE-2.0
// 
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
// ============================================================================
package com.braintribe.gwt.async.client;

import java.util.function.Function;

import com.braintribe.codec.Codec;
import com.google.gwt.user.client.rpc.AsyncCallback;

public abstract class LoaderChain<T> implements Loader<T> {
	public static <T> LoaderChainImpl<T, T, FragmentChainType<T>> beginFragment() {
		FragmentChainType<T> fragmentChainType = new FragmentChainType<T>();
		return new LoaderChainImpl<T, T, FragmentChainType<T>>(fragmentChainType, fragmentChainType);
	}

	public static <T> LoaderChainImpl<T, T, RootChainType<T>> begin(Loader<T> loader) {
		return new LoaderChainImpl<T, T, RootChainType<T>>(new RootChainType<T>(), loader);
	}

	public static <T> LoaderChainImpl<ResultBundle, ResultBundle, RootChainType<ResultBundle>> begin(final String key, final Loader<T> loader) {
		Loader<ResultBundle> firstLoader = new Loader<ResultBundle>() {
			@Override
			public void load(final AsyncCallback<ResultBundle> callback) {
				loader.load(new AsyncCallback<T>() {
					@Override
					public void onSuccess(T result) {
						ResultBundle resultBundle = new ResultBundle();
						resultBundle.set(key, result);
						callback.onSuccess(resultBundle);
					}
					
					@Override
					public void onFailure(Throwable caught) {
						callback.onFailure(caught);
					}
				});
			}
		};
		return new LoaderChainImpl<ResultBundle, ResultBundle, RootChainType<ResultBundle>>(new RootChainType<ResultBundle>(), firstLoader);
	}

	public abstract <N> LoaderChain<N> append(ChainedLoader<T, N> successor);
	public abstract LoaderChain<ResultBundle> append(String key, Loader<?> successor);
	
	public abstract <N> LoaderChain<N> decode(final Codec<? extends N, ? super T> codec);
	public abstract <N> LoaderChain<N> encode(final Codec<? super T, ? extends N> codec);
	public abstract <N> LoaderChain<N> transform(final Function<? super T, ? extends N> indexedProvider);
	public abstract LoaderChain<T> process(final Processor<T> processor);
	public abstract LoaderChain<T> throwException(Throwable t);

	public interface Join<L, R> {

		public <O> LoaderChain<O> merge(final Merger<L, R, O> merger);
		
		public LoaderChain<L> mergeLeft(Merger<L, R, L> merger);
		
		public LoaderChain<R> mergeRight(Merger<L, R, R> merger);
		
		public LoaderChain<L> end(); 
	}
	
	public abstract <E> Join<T, E> join(final Loader<E> successor);
	public abstract <E> Join<T, E> join(final ChainedLoader<T, E> successor);
	
	public Future<T> load() {
		Future<T> future = new Future<T>();
		load(future);
		return future;
	}
}
