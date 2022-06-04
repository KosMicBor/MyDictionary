package kosmicbor.mydictionary.model.domain

import io.reactivex.rxjava3.core.Observable

interface DataSource<T> {
    fun getData(lookupWord: String, translationDirection: String): Observable<T>
}