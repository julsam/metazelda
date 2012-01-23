package net.bytten.metazelda.algo;

import java.util.Random;

public class DungeonGenerator {
    
    public static final int MAX_ELEMS = 26;

    // The actual core algorithm from
    // http://bytten.net/devlog/2012/01/21/procedural-dungeon-generation-part-i/
    // Lines marked with XXX are where tweakable probabilities are. Play around
    // with them to see if you can generate better dungeons.
    // TODO backedges
    public static Room addItemPath(Random rand, Dungeon dungeon, Element item) {
        // Add a new room to the dungeon containing the given item. Conditions
        // to enter the room are randomly generated, and if requiring a new item
        // in the dungeon, will cause other rooms to be added, too.
        Condition cond = null;
        
        // Choose condition to enter the room
        float r = rand.nextFloat();
        if (dungeon.elementCount() < MAX_ELEMS && r < 0.2) {    // XXX
            // create a new condition and item for it
            Element elem = dungeon.makeNewElement();
            cond = new Condition(elem);
            addItemPath(rand, dungeon, elem);
        } else if (r < 0.6) {                                   // XXX
            // make the condition one which we've used before
            Element elem = dungeon.getRandomPlacedElement(rand);
            if (elem != null)
                cond = new Condition(elem);
        }
        
        // Choose where to place the new room
        Room locRoom = null;
        Integer locD = null;
        r = rand.nextFloat();
        if (dungeon.roomCount() < 20 && r < 0.7) {              // XXX
            // Add padding rooms (and potentially more conditions and branches
            // along the way)
            locRoom = addItemPath(rand, dungeon, null);
            locD = dungeon.getRandomAdjacentSpaceDirection(rand, locRoom);
            // addItemPath can create a room with no adjacent spaces, so
            // loc.second (the direction to add the new room in) might still be
            // null.
        }
        
        if (locRoom == null || locD == null) {
            // Choose an existing room with a free edge
            locRoom  = dungeon.getRandomExternalRoom(rand);
            locD = dungeon.getRandomAdjacentSpaceDirection(rand, locRoom);
        }
        
        // Finally create the new room and link it to the parent room
        Room room = new Room(locRoom.coords.nextInDirection(locD), item);
        dungeon.add(room);
        dungeon.link(locRoom, room, cond);
        
        return room;
    }
    
    public static Dungeon generate(Random rand) {
        Dungeon dungeon = new Dungeon();
        Room startRoom = new Room(0,0, null);
        startRoom.setItem(new Element(Element.START));
        dungeon.add(startRoom);
        
        addItemPath(rand, dungeon, new Element(Element.GOAL));
        
        return dungeon;
    }
    
}