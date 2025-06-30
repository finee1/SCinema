package com.example.skillcinema.entity


interface Filter {
    val genres: List<GenreFilter>
    val countries: List<CountryFilter>
}