package com.epam.dao.task1.task;

import com.epam.dao.task1.entity.Car;
import com.epam.dao.task1.entity.Vehicle;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;
import java.util.List;


public class ContainerTest extends Container {

    private List<Vehicle> container;

    @Before
    public void setUp() {
        container = new Container();
    }

    @After
    public void tearDown() {
        container = null;
    }


    @Test
    public void should_add_element() {
        addVehicleToArray();
        int expectedSize = 3;
        int actualySize = container.size();
        Assert.assertEquals(expectedSize, actualySize);

    }

    @Test
    public void should_add_element_by_index() {
        addVehicleToArray();
        Vehicle vehicle = new Vehicle("FIRST", 1997, "BMW", 250);
        container.add(1,vehicle);
        int expectIndex = 1;
        int actualIndex = container.indexOf(vehicle);
        Assert.assertEquals(expectIndex,actualIndex);
    }
    @Test
    public void should_get_the_same_element_by_index() {
        addVehicleToArray();

        Vehicle expectIndex = container.get(0);
        Vehicle actualIndex = container.get(1);

        int expectSize = 3;
        int actualSize = container.size();

        Assert.assertNotEquals(expectIndex,actualIndex);
        Assert.assertEquals(expectSize,actualSize);
    }
    @Test
    public void should_remove_element_by_index() {
        addVehicleToArray();

        int expectSize = 3;
        int actualSize = container.size();
        Assert.assertEquals(expectSize,actualSize);

        container.remove(1);

        expectSize = 2;
        actualSize = container.size();
        Assert.assertEquals(expectSize,actualSize);

    }
    @Test
    public void should_remove_element_by_object() {

        addVehicleToArray();
        Vehicle vehicle = new Vehicle("FIRST", 1997, "BMW", 250);

        int expectSize = 3;
        int actualSize = container.size();
        Assert.assertEquals(expectSize,actualSize);

        container.remove(vehicle);

        expectSize = 2;
        actualSize = container.size();
        Assert.assertEquals(expectSize,actualSize);
    }

    @Test
    public void should_throw_true_when_hasNext_called() {
        addVehicleToArray();
        Iterator iterator = container.iterator();
        Assert.assertTrue(iterator.hasNext());

    }



    private void addVehicleToArray(){
        container.add(new Vehicle("BCS", 1997, "BMW", 250));
        container.add(new Vehicle("VCL", 1997, "BMW", 240));
        container.add(new Vehicle("FIRST", 1997, "BMW", 250));
    }



}