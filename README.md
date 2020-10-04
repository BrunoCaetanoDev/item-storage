# item-storage
[![License](https://img.shields.io/github/license/BrunoaccDev/item-storage)](https://github.com/BrunoaccDev/item-storage/blob/master/LICENSE)

A small, decoupled and domain-driven cloud-ready microservice based in SpringBoot that aims to manage a product catalogue and storage facility.

##Terminology
| Name | Description |
|:----------:|:-------------|
| items | A service managed model entity that retains various information associated with a specific product. |


##Operations
| Name | Description |
|:----------:|:-------------|
| getItems | Synchronous operation that allows the retrieval of multiples persisted item entries. |
| getItem |  Synchronous operation that allows the retrieval of a single persisted item entry. |
| createItem |  Synchronous operation that allows the persistence of a single item entry. |
| updateItem | Synchronous operation that allows the update of a single persisted item entry. |
| deleteItem | Synchronous operation that allows the removal of a single persisted item entry. |
| restockItem | Synchronous operation that allows to restock the stock count of a single persisted item entry bt a given amount. |
| dispatchItem | Synchronous operation that allows to dispatch the stock count of a single persisted item entry bt a given amount. |

