package org.example;
import javax.persistence.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class App {
    static EntityManagerFactory emf;
    static EntityManager em;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            // create connection
            emf = Persistence.createEntityManagerFactory("JPATest");
            em = emf.createEntityManager();
            try {
                while (true) {
                    System.out.println("1: add dish");
                    System.out.println("2: add random dishes");
                    System.out.println("3: delete dish");
                    System.out.println("4: change dish");
                    System.out.println("5: view menu");
                    System.out.println("6: dish selection by criteria");
                    System.out.print("-> ");

                    String s = sc.nextLine();
                    switch (s) {
                        case "1":
                            addDish(sc);
                            break;
                        case "2":
                            insertRandomDishes(sc);
                            break;
                        case "3":
                            deleteDish(sc);
                            break;
                        case "4":
                            changeDish(sc);
                            break;
                        case "5":
                            viewMenu();
                            break;
                        case "6":
                            dishSelection(sc );
                            break;
                        default:
                            return;
                    }
                }
            } finally {
                sc.close();
                em.close();
                emf.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return;
        }
    }

    private static void addDish(Scanner sc) {
        System.out.print("Enter dish name: ");
        String name = sc.nextLine();
        System.out.print("Enter dish cost: ");
        String sCost = sc.nextLine();
        double cost = Double.parseDouble(sCost);
        System.out.print("Enter dish weight: ");
        String sWeight = sc.nextLine();
        double weight = Double.parseDouble(sWeight);
        System.out.print("Is there a discount?: yes/no ");
        String sDiscount = sc.nextLine();
        boolean discount = sDiscount.equals("yes");

        em.getTransaction().begin();
        try {
            Dish c = new Dish(name, cost, weight, discount);
            em.persist(c);
            em.getTransaction().commit();

            System.out.println(c.getId());
        } catch (Exception ex) {
            em.getTransaction().rollback();
        }
    }

    private static void deleteDish(Scanner sc) {
        System.out.print("Enter dish id: ");
        String sId = sc.nextLine();
        long id = Long.parseLong(sId);

        Dish c = em.getReference(Dish.class, id);
        if (c == null) {
            System.out.println("Dish not found!");
            return;
        }

        em.getTransaction().begin();
        try {
            em.remove(c);
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
        }
    }

    private static void changeDish(Scanner sc) {
        System.out.println("What would you like to change?");
        System.out.println("1: change name");
        System.out.println("2: change cost");
        System.out.println("3: change weight");
        System.out.println("4: change discount");
        System.out.print("-> ");

        String s = sc.nextLine();
        switch (s) {
            case "1":
                changeName(sc);
                break;
            case "2":
                changeCost(sc);
                break;
            case "3":
                changeWeight(sc);
                break;
            case "4":
                changeDiscount(sc);
                break;
            default:
                return;
        }
    }
        private static void changeCost(Scanner sc) {
            System.out.print("Enter dish id: ");
            String sId = sc.nextLine();
            long id = Long.parseLong(sId);

            Dish c = em.getReference(Dish.class, id);
            if (c == null) {
                System.out.println("Dish not found!");
                return;
            }

            System.out.print("Enter new cost: ");
            String sCost = sc.nextLine();
            double cost = Double.parseDouble(sCost);

            Dish c1 = null;
            try {
                Query query = em.createQuery("SELECT x FROM Dish x WHERE x.id = :id", Dish.class);
                query.setParameter("id", id);

                c1 = (Dish) query.getSingleResult();
            } catch (NoResultException ex) {
                System.out.println("Dish not found!");
                return;
            } catch (NonUniqueResultException ex) {
                System.out.println("Non unique result!");
                return;
            }

            em.getTransaction().begin();
            try {
                c.setCost(cost);
                em.getTransaction().commit();
            } catch (Exception ex) {
                em.getTransaction().rollback();
            }
        }

    private static void changeName(Scanner sc) {
        System.out.print("Enter dish id: ");
        String sId = sc.nextLine();
        long id = Long.parseLong(sId);

        Dish c = em.getReference(Dish.class, id);
        if (c == null) {
            System.out.println("Dish not found!");
            return;
        }

        System.out.print("Enter new name: ");
        String name = sc.nextLine();

        Dish c1 = null;
        try {
            Query query = em.createQuery("SELECT x FROM Dish x WHERE x.id = :id", Dish.class);
            query.setParameter("id", id);

            c1 = (Dish) query.getSingleResult();
        } catch (NoResultException ex) {
            System.out.println("Dish not found!");
            return;
        } catch (NonUniqueResultException ex) {
            System.out.println("Non unique result!");
            return;
        }

        em.getTransaction().begin();
        try {
            c.setName(name);
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
        }
    }
    private static void changeWeight(Scanner sc) {
        System.out.print("Enter dish id: ");
        String sId = sc.nextLine();
        long id = Long.parseLong(sId);

        Dish c = em.getReference(Dish.class, id);
        if (c == null) {
            System.out.println("Dish not found!");
            return;
        }

        System.out.print("Enter new weight: ");
        String sWeight = sc.nextLine();
        double weight = Double.parseDouble(sWeight);

        Dish c1 = null;
        try {
            Query query = em.createQuery("SELECT x FROM Dish x WHERE x.id = :id", Dish.class);
            query.setParameter("id", id);

            c1 = (Dish) query.getSingleResult();
        } catch (NoResultException ex) {
            System.out.println("Dish not found!");
            return;
        } catch (NonUniqueResultException ex) {
            System.out.println("Non unique result!");
            return;
        }

        em.getTransaction().begin();
        try {
            c.setWeight(weight);
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
        }
    }
    private static void changeDiscount(Scanner sc) {
        System.out.print("Enter dish id: ");
        String sId = sc.nextLine();
        long id = Long.parseLong(sId);

        Dish c = em.find(Dish.class, id);
        if (c == null) {
            System.out.println("Dish not found!");
            return;
        }

        em.getTransaction().begin();
        try {
            // Змінюємо значення знижки на протилежне
            c.setDiscount(!c.isDiscount());
            em.getTransaction().commit();
            System.out.println("Discount for Dish with ID " + id + " has been changed to: " + c.isDiscount());
        } catch (Exception ex) {
            em.getTransaction().rollback();
        }
    }

    private static void insertRandomDishes(Scanner sc) {
        System.out.print("Enter dishes count: ");
        String sCount = sc.nextLine();
        int count = Integer.parseInt(sCount);

        em.getTransaction().begin();
        try {
            for (int i = 0; i < count; i++) {
                Dish c = new Dish(randomName(), RND.nextDouble(1200),RND.nextDouble(1000), RND.nextBoolean());
                em.persist(c);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
        }
    }

    private static void viewMenu() {
        Query query = em.createQuery("SELECT c FROM Dish c", Dish.class);
        List<Dish> list = (List<Dish>) query.getResultList();

        for (Dish c : list)
            System.out.println(c);
    }
    private static void dishSelection(Scanner sc) throws SQLException {
        System.out.println("What criteria do you want to use to select a dish?");
        System.out.println("1: for the cost");
        System.out.println("2: discounted dishes");
        System.out.println("3: a set of dishes, the total weight of which is not more than 1 kg");
        System.out.print("-> ");

        String s = sc.nextLine();
        switch (s) {
            case "1":
                selectionByCost(sc);
                break;
            case "2":
                selectionByDiscount();
                break;
            case "3":
                selectionByWeight();
                break;
            default:
                return;
        }
    }

    private static void selectionByCost(Scanner sc) throws SQLException {
        System.out.println("Enter minimum price:");
        String costMin = sc.nextLine();

        System.out.println("Enter maximum price:");
        String costMax = sc.nextLine();

        try {
            Query query = em.createQuery("SELECT c FROM Dish c WHERE c.cost >= " + costMin + " AND " + "c.cost <= "
                    + costMax, Dish.class);
            List<Dish> list = (List<Dish>) query.getResultList();
            for (Dish c : list) {
                System.out.println(c);
            }
        } catch (NoResultException e) {
            System.out.println("Dish not found");
            return;
        }
    }
    private static void selectionByDiscount() throws SQLException {

        try {
            Query query = em.createQuery("SELECT c FROM Dish c WHERE c.discount = true", Dish.class);
            List<Dish> list = (List<Dish>) query.getResultList();
            for (Dish c : list) {
                System.out.println(c);
            }
        } catch (NoResultException e) {
            System.out.println("Dish not found");
            return;
        }
    }
    private static void selectionByWeight() throws SQLException {
        try {
            Query query = em.createQuery("SELECT c FROM Dish c ORDER BY c.weight", Dish.class);

            List<Dish> list = (List<Dish>) query.getResultList();
            if (list.isEmpty()) {
                System.out.println("No dishes found.");
                return;
            }

            double totalWeight = 0;
            List<Dish> selectedDishes = new ArrayList<>();

            for (Dish dish : list) {
                if (totalWeight + dish.getWeight() <= 1000) {
                    selectedDishes.add(dish);
                    totalWeight += dish.getWeight();
                } else {
                    break;
                }
            }

            if (selectedDishes.isEmpty()) {
                System.out.println("No dishes found with total weight less than or equal to 1 kg");
                return;
            }

            System.out.println("Dishes with total weight less than or equal to 1 kg:");
            for (Dish dish : selectedDishes) {
                System.out.println(dish);
            }
        } catch (NoResultException e) {
            System.out.println("Dish not found");
            return;
        }
    }


    static final String[] NAMES = {"Margarita pizza", "Hawaiian pizza", "BBQ pizza", "4-cheese pizza", "4-meat pizza"};
    static final Random RND = new Random();

    static String randomName() {
        return NAMES[RND.nextInt(NAMES.length)];
    }
}


