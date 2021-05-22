SELECT EXISTS(
               SELECT 1 FROM consumed_event WHERE id = /* id */'id' AND receiver = /* receiver */'receiver'
           )