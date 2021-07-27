package com.example.parcer


import org.jsoup.Jsoup

data class Stat(val name: String, val modif : String)

data class Monster(val name : String, val size : String, val type : String, val hits : String,
                   val kd : String, val speed : String, val stats : ArrayList<Stat>, val skills : String,
                   val actionAndDescription : String,)

const val baseUrl = "https://dungeon.su"

fun getDbMagick () : ArrayList<Magick> {

    val urlSpells = "https://dungeon.su/spells/"

    val arrayList = ArrayList<String>()
    val spellArray = ArrayList<Magick>()


    val documentSpells = Jsoup.connect(urlSpells).timeout(180000).get()
       val elementSpells = documentSpells.select("div[class=card-wrapper]")
           .select("ul[class=list-of-items col4 double]")
           .select("li[class]")

       for (i in 0 until elementSpells.size) {
                   val spellName = elementSpells.select("a")
                       .eq(i)
                       .attr("href")

                   if (spellName.isNotEmpty()) arrayList.add(baseUrl + spellName)
               }
       for (index in arrayList.indices) {

                   val spellUrl = arrayList[index]
                   val documentSpell = Jsoup.connect(spellUrl).get()
                   val elementSpell = documentSpell.select("div[class=card-wrapper]")

                   val nameAndSchool = (elementSpell.select("div[class=card-body]")
                       .select("ul[class=params]")
                       .select("li[class=size-type-alignment]")
                       .text()
                       .split(", "))


                   val magick = Magick(
                       name = elementSpell.select("div[class=card-header]")
                           .select("h2[class=card-title iconed]")
                           .select("a")
                           .text(),

                       spellLvl = nameAndSchool[0],
                       range = elementSpell.select("div[class=card-body]")
                           .select("ul[class=params]")
                           .select("li")
                           .eq(1)
                           .text(),
                       school = nameAndSchool[1],
                       time = elementSpell.select("div[class=card-body]")
                           .select("ul[class=params]")
                           .select("li")
                           .eq(2)
                           .text(),
                       component = elementSpell.select("div[class=card-body]")
                           .select("ul[class=params]")
                           .select("li")
                           .eq(3)
                           .text(),
                       duration = elementSpell.select("div[class=card-body]")
                           .select("ul[class=params]")
                           .select("li")
                           .eq(4)
                           .text(),
                       persClasses = elementSpell.select("div[class=card-body]")
                           .select("ul[class=params]")
                           .select("li")
                           .eq(5)
                           .text(),
                       soorce = elementSpell.select("div[class=card-body]")
                           .select("ul[class=params]")
                           .select("li")
                           .eq(6)
                           .select("strong")
                           .attr("title"),
                       description = (elementSpell.select("div[class=card-body]")
                           .select("ul[class=params]")
                           .select("li")
                           .eq(7)
                           .select("div")
                           .select("p")
                           .eq(0)
                           .text() + '\n' +
                               elementSpell.select("div[class=card-body]")
                                   .select("ul[class=params]")
                                   .select("li")
                                   .eq(7)
                                   .select("div")
                                   .select("p")
                                   .eq(1)
                                   .text())
                   )

                   spellArray.add(magick)
               }

   return spellArray

}

fun getDbMonster() : ArrayList<Monster> {
    val urlMonsters = "https://dungeon.su/bestiary/"
    val monsterArray = ArrayList<Monster>()
    val monstersUrlList = ArrayList<String>()

    val documentMonster = Jsoup.connect(urlMonsters).timeout(180000).get()
    val elementMonsters = documentMonster.select("ul[class=list-of-items col4 double]")
        .select("li[class]")

    for (i in 0 until elementMonsters.size){
        val monsterName = elementMonsters.select("a")
            .eq(i)
            .attr("href")

        if (monsterName.isNotEmpty()) monstersUrlList.add(baseUrl + monsterName)
    }

    for (index in monstersUrlList.indices){
        val monsterDoc = Jsoup.connect(monstersUrlList[index]).get()
        val monsterElement = monsterDoc.select("div[class=card-wrapper]")

        val monsterName = monsterElement.select("div[class=card-header]")
            .select("h2[class=card-title iconed]")
            .select("a")
            .text()


        val sizeAndType = monsterElement.select("ul[class=params]")
            .select("li[class=size-type-alignment]")
            .text()
            .split(", ")
        val monsterSize = sizeAndType[0]
        val monsterType = sizeAndType[1]

        val monsterKd = monsterElement.select("ul[class=params]")
            .select("li")
            .eq(1)
            .text()

        val monsterHitsElement = monsterElement.select("ul[class=params]")
            .select("li")
            .eq(2)
            .select("span")


        var monsterHits = ""
        for (i in 0 until monsterHitsElement.size){
            monsterHits += monsterHitsElement.eq(i)
                .text()
            when (i) {
                0 -> monsterHits += " ("
                1 -> monsterHits += "к"
            }
        }
        monsterHits += ")"

        val monsterSpeed = monsterElement.select("ul[class=params]")
            .select("li")
            .eq(3)
            .select("strong")
            .eq(1)
            .text()

        val monsterStats = ArrayList<Stat>()
        val monsterStatElement = monsterElement.select("ul[class=params]")
            .select("li")
            .eq(4)

        for (i in 0 until 6){
            val statName = monsterStatElement.select("div[class=stat]")
                .eq(i)
                .attr("title")


            val statModif = monsterStatElement.select("div[class=stat]")
                .eq(i)
                .select("strong")
                .text()

            monsterStats.add(Stat(statName,statModif))
        }

        var monsterSkills = ""
        for (i in 5 until monsterElement.select("ul[class=params]")
            .select("li")
            .size)
        {

            val monsterSkillsApr = monsterElement.select("ul[class=params]")
                .select("ul.params>li:not(.subsection)")
                .eq(i)
                .text()


            if(("Монстра добавил" !in monsterSkillsApr)&&(monsterSkillsApr.isNotEmpty()))
                monsterSkills += (monsterSkillsApr + '\n')

        }


        var monsterAction = ""
        for (i in 0 until monsterElement.select("ul[class=params]")
            .select("li.subsection")
            .size)
        {
            monsterAction += (monsterElement.select("ul[class=params]")
                .select("li.subsection")
                .eq(i)
                .text() + '\n')
        }

        val monster = Monster(monsterName,monsterSize,monsterType,monsterHits,monsterKd,monsterSpeed,monsterStats,
            monsterSkills,monsterAction)
        monsterArray.add(monster)

    }
    return monsterArray
}


