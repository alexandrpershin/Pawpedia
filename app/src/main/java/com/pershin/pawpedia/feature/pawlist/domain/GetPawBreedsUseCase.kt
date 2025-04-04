package com.pershin.pawpedia.feature.pawlist.domain

import com.pershin.pawpedia.core.PawBreedRepository
import com.pershin.pawpedia.core.network.BaseResponse
import com.pershin.pawpedia.core.network.BreedDto
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import javax.inject.Inject

class GetPawBreedsUseCase @Inject constructor(
    private val repository: PawBreedRepository
) {

    suspend operator fun invoke(): Result<List<PawBreed>> {
        return repository.getPawBreeds()

//        return getMockItems()
    }

    // TODO remove
    private fun getMockItems(): Result<List<PawBreed>> {
        val items = adapter.fromJson(response)

        return Result.success(items!!.message.map {
            PawBreed(name = it.key, subBreeds = it.value)
        })

    }

    // TODO remove

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    // TODO remove

    @OptIn(ExperimentalStdlibApi::class)
    val adapter = moshi.adapter<BaseResponse<Map<String, List<String>>>>()

    // TODO remove

    private val response = """
        {"message":{"affenpinscher":[],"african":[],"airedale":[],"akita":[],"appenzeller":[],"australian":["kelpie","shepherd"],"bakharwal":["indian"],"basenji":[],"beagle":[],"bluetick":[],"borzoi":[],"bouvier":[],"boxer":[],"brabancon":[],"briard":[],"buhund":["norwegian"],"bulldog":["boston","english","french"],"bullterrier":["staffordshire"],"cattledog":["australian"],"cavapoo":[],"chihuahua":[],"chippiparai":["indian"],"chow":[],"clumber":[],"cockapoo":[],"collie":["border"],"coonhound":[],"corgi":["cardigan"],"cotondetulear":[],"dachshund":[],"dalmatian":[],"dane":["great"],"danish":["swedish"],"deerhound":["scottish"],"dhole":[],"dingo":[],"doberman":[],"elkhound":["norwegian"],"entlebucher":[],"eskimo":[],"finnish":["lapphund"],"frise":["bichon"],"gaddi":["indian"],"germanshepherd":[],"greyhound":["indian","italian"],"groenendael":[],"havanese":[],"hound":["afghan","basset","blood","english","ibizan","plott","walker"],"husky":[],"keeshond":[],"kelpie":[],"kombai":[],"komondor":[],"kuvasz":[],"labradoodle":[],"labrador":[],"leonberg":[],"lhasa":[],"malamute":[],"malinois":[],"maltese":[],"mastiff":["bull","english","indian","tibetan"],"mexicanhairless":[],"mix":[],"mountain":["bernese","swiss"],"mudhol":["indian"],"newfoundland":[],"otterhound":[],"ovcharka":["caucasian"],"papillon":[],"pariah":["indian"],"pekinese":[],"pembroke":[],"pinscher":["miniature"],"pitbull":[],"pointer":["german","germanlonghair"],"pomeranian":[],"poodle":["medium","miniature","standard","toy"],"pug":[],"puggle":[],"pyrenees":[],"rajapalayam":["indian"],"redbone":[],"retriever":["chesapeake","curly","flatcoated","golden"],"ridgeback":["rhodesian"],"rottweiler":[],"saluki":[],"samoyed":[],"schipperke":[],"schnauzer":["giant","miniature"],"segugio":["italian"],"setter":["english","gordon","irish"],"sharpei":[],"sheepdog":["english","indian","shetland"],"shiba":[],"shihtzu":[],"spaniel":["blenheim","brittany","cocker","irish","japanese","sussex","welsh"],"spitz":["indian","japanese"],"springer":["english"],"stbernard":[],"terrier":["american","australian","bedlington","border","cairn","dandie","fox","irish","kerryblue","lakeland","norfolk","norwich","patterdale","russell","scottish","sealyham","silky","tibetan","toy","welsh","westhighland","wheaten","yorkshire"],"tervuren":[],"vizsla":[],"waterdog":["spanish"],"weimaraner":[],"whippet":[],"wolfhound":["irish"]},"status":"success"}
    """.trimIndent()

}